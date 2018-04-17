package com.tresor.statistic.totalspending.activity

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.tresor.R
import com.tresor.common.activity.DateSelectorActivity
import com.tresor.common.model.viewmodel.SpendingModel
import kotlinx.android.synthetic.main.activity_total_spending.*
import kotlinx.android.synthetic.main.date_selector_header.*

class TotalSpendingActivity : DateSelectorActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_total_spending
    }

    override fun startDateEditText(): EditText {
        return startDateField
    }

    override fun endDateEditText(): EditText {
        return endDateField
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hashtagLineChart.apply {
            axisLeft.setDrawGridLines(false)
            axisLeft.setDrawLabels(false)
            xAxis.setDrawGridLines(false)
            legend.isEnabled = false
            setDrawGridBackground(false)
            axisRight.setDrawGridLines(false)
            description.textSize = 16f
        }
        setData()
    }

    //TODO kalo udah ambil data dari inet pass TotalSpendingModel di parameter
    private fun setData() {
        val lineDataSet = LineDataSet(setLineYAxis(dummyTotalSpendingModel()),
                "Amount of Money Spent"
        )
        setLineDataSet(lineDataSet, resources.getColor(R.color.pie4), true)
        val lineData = LineData(lineDataSet)
        lineData.setValueTextSize(10f)
        lineData.setDrawValues(false)

        hashtagLineChart.xAxis.setValueFormatter(
                { value, axis ->  dateValueFormatter(dummyTotalSpendingModel(), value)})
        hashtagLineChart.data = lineData
        hashtagLineChart.animateX(1000)

        val latestIndexData : SpendingModel = dummyTotalSpendingModel().last()

        highlightedSpending.setText(latestIndexData.date + ":" + latestIndexData.amount)
        hashtagLineChart.setOnChartValueSelectedListener(ChartValueListener(
                dummyTotalSpendingModel(),
                highlightedSpending,
                goToDetailButton)
        )
        goToDetailButton.setOnClickListener { latestIndexData.id }
        hashtagLineChart.description.text = "Total Spending: " + latestIndexData.amount
    }

    private fun setLineYAxis(spendingModels: List<SpendingModel>): List<Entry> {
        val entries: ArrayList<Entry> = arrayListOf()
        var sumOfSpending = 0.0
        for ((index, spendingModel: SpendingModel) in spendingModels.withIndex()) {
            sumOfSpending += spendingModel.amountUnformatted
            val entry = Entry(index.toFloat(), sumOfSpending.toFloat())
            entries.add(entry)
        }
        return entries
    }

    private fun setLineDataSet(dataSet: LineDataSet, color: Int, drawFilled: Boolean) {
        dataSet.setDrawCircles(true)
        dataSet.setDrawFilled(drawFilled)
        if (drawFilled) {
            dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            dataSet.setCubicIntensity(0.2f)
        }
        dataSet.fillColor = color
        dataSet.lineWidth = 1.8f
        dataSet.circleRadius = 4f
        dataSet.setCircleColor(color)
        //dataSet.setHighLightColor(Color.rgb(244, 117, 117));
        dataSet.setDrawCircleHole(false)
        dataSet.highlightLineWidth = 1.5f
        dataSet.highLightColor = color
        dataSet.color = color
        dataSet.fillAlpha = 100
        dataSet.setDrawHorizontalHighlightIndicator(true)
        dataSet.fillFormatter = IFillFormatter { _, _ -> -10f }
    }

    private fun dummyTotalSpendingModel(): List<SpendingModel> {

        var index = 0

        val spendingModelList: ArrayList<SpendingModel> = arrayListOf()

        val dummyHashTag: ArrayList<String> = arrayListOf()

        dummyHashTag.add("Makan")
        dummyHashTag.add("Minum")
        dummyHashTag.add("Boker")
        while (index < 12) {
            spendingModelList.add(SpendingModel(
                    index,
                    "Rp 12000",
                    Math.random() * 10000,
                    true, 1,
                    "#Makan#Ancur#Blablaba",
                    "12 December 2012",
                    1,
                    dummyHashTag
            ))
            index++
        }
        return spendingModelList
    }

    private fun dateValueFormatter(spendingModels: List<SpendingModel>, value: Float): String {
        return spendingModels.get(Math.round(value)).date
    }

    private class ChartValueListener(val spendingModels : List<SpendingModel>,
                             val highlightedSpending : TextView,
                             val goToDetailButton : TextView) : OnChartValueSelectedListener {

        override fun onNothingSelected() {

        }

        override fun onValueSelected(entry: Entry?, highlight: Highlight?) {
            val currenSpendingModels = spendingModels.get(Math.round(entry!!.x))
            highlightedSpending.setText(
                    currenSpendingModels.date + " " + currenSpendingModels.amount
            )

            goToDetailButton.setOnClickListener {  }
        }
    }

    

}
