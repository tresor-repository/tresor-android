package com.tresor.statistic.totalspending.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
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
import com.tresor.common.model.viewmodel.DailySpendingModel
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.statistic.totalspending.adapter.SortingSpinnerAdapter
import com.tresor.statistic.totalspending.adapter.TopSpendingAdapter
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
        setData(dummyDailySpendingModelList())
    }

    //TODO kalo udah ambil data dari inet pass TotalSpendingModel di parameter
    private fun setData(dailySpendings: MutableList<DailySpendingModel>) {
        val dailySpendingsWithCalculatedSum = calculateTotalSpendingSum(dailySpendings)
        setGraph(dailySpendingsWithCalculatedSum)
        setSpendingTable(dailySpendingsWithCalculatedSum)
    }

    private fun setGraph(dailySpendingsWithCalculatedSum: MutableList<DailySpendingModel>) {
        val lineDataSet = LineDataSet(setLineYAxis(dailySpendingsWithCalculatedSum),
                "Amount of Money Spent")
        configureLineDataSet(lineDataSet, resources.getColor(R.color.pie4), true)
        val lineData = LineData(lineDataSet).apply {
            setValueTextSize(10f)
            setDrawValues(false)
        }
        hashtagLineChart.data = lineData
        hashtagLineChart.xAxis.setDrawLabels(false)
        hashtagLineChart.axisRight.setDrawLabels(false)
        setLastIndexValue(dailySpendingsWithCalculatedSum)
    }

    private fun setLastIndexValue(dailySpendingsWithCalculatedSum: MutableList<DailySpendingModel>) {
        val latestIndexData = dailySpendingsWithCalculatedSum.last()
        highlightedSpending.setText(latestIndexData.date + ":" + latestIndexData.summarizedTotalAmount)
        hashtagLineChart.setOnChartValueSelectedListener(ChartValueListener(
                dailySpendingsWithCalculatedSum,
                highlightedSpending,
                goToDetailButton)
        )
        goToDetailButton.setOnClickListener { latestIndexData.id }
        totalSpendingText.text = latestIndexData.summarizedTotalAmount.toString()
        hashtagLineChart.description.isEnabled = false
    }


    private fun setLineYAxis(dailySpendings: MutableList<DailySpendingModel>): MutableList<Entry> {
        val entries: MutableList<Entry> = mutableListOf()
        dailySpendings.mapIndexedTo(entries) { index, dailySpending ->
            Entry(index.toFloat(), dailySpending.summarizedTotalAmount)
        }
        return entries
    }

    private fun setSpendingTable(dailySpendingsWithCalculatedSum: MutableList<DailySpendingModel>) {
        topSpendingRecyclerView.layoutManager = LinearLayoutManager(this)
        val topSpendingAdapter = TopSpendingAdapter(dailySpendingsWithCalculatedSum)
        topSpendingRecyclerView.adapter = topSpendingAdapter
        listSortingSpinner.adapter = SortingSpinnerAdapter(this)
        listSortingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> topSpendingAdapter.onHighestSpendingClicked()
                    1 -> topSpendingAdapter.onLowestSpendingClicked()
                    else -> topSpendingAdapter.onSortByDateClicked()
                }
            }
        }
    }

    private fun configureLineDataSet(dataSet: LineDataSet, color: Int, drawFilled: Boolean)
            = with(dataSet) {
        setDrawCircles(true)
        setDrawFilled(drawFilled)
        if (drawFilled) {
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setCubicIntensity(0.2f)
        }
        fillColor = color
        lineWidth = 1.8f
        circleRadius = 4f
        setCircleColor(color)
        //dataSet.setHighLightColor(Color.rgb(244, 117, 117));
        setDrawCircleHole(false)
        highlightLineWidth = 1.5f
        highLightColor = color
        dataSet.color = color
        fillAlpha = 100
        setDrawHorizontalHighlightIndicator(true)
        fillFormatter = IFillFormatter { _, _ -> -10f }
    }

    private fun calculateTotalSpendingSum(dailySpendings: MutableList<DailySpendingModel>): MutableList<DailySpendingModel> {
        return dailySpendings.mapIndexed { index, dailySpendingModel ->
            dailySpendingModel.totalAmount = calculateDailyAmount(dailySpendingModel.listOfSpending)
            when (index) {
                0 -> dailySpendingModel.summarizedTotalAmount = dailySpendingModel.totalAmount
                else -> dailySpendingModel.summarizedTotalAmount =
                        dailySpendingModel.totalAmount + dailySpendings[index - 1]
                                .summarizedTotalAmount
            }
            dailySpendingModel
        }.toMutableList()
    }

    private fun calculateDailyAmount(spendings: List<SpendingModel>): Float {
        return spendings.map { it.amountUnformatted.toFloat() }.sum()
    }

    private fun dummyTotalSpendingModel(): List<SpendingModel> {

        var index = 0

        val spendingModelList: ArrayList<SpendingModel> = arrayListOf()

        val dummyHashTag: ArrayList<String> = arrayListOf()

        dummyHashTag.add("Makan")
        dummyHashTag.add("Minum")
        dummyHashTag.add("Boker")
        while (index < 14) {
            spendingModelList.add(SpendingModel(
                    index,
                    "Rp 12000",
                    Math.random() * 10000,
                    true, 1,
                    "#Makan#Ancur#Blablaba",
                    "12 December 2012",
                    1,
                    dummyHashTag,
                    ""
            ))
            index++
        }
        return spendingModelList
    }

    private fun dummyDailySpendingModelList(): MutableList<DailySpendingModel> {

        val dummyDailySpendings: MutableList<DailySpendingModel> = mutableListOf()

        var index = 0

        while (index < 13) {
            val dummyDailySpending = DailySpendingModel(
                    index.toString(),
                    (index + 1).toString() + "  December",
                    dummyTotalSpendingModel())
            dummyDailySpending.summarizedTotalAmount = calculateDailyAmount(dummyDailySpending.listOfSpending)
            dummyDailySpendings.add(dummyDailySpending)
            index++
        }

        return dummyDailySpendings
    }

    private class ChartValueListener(val spendingModels: List<DailySpendingModel>,
                                     val highlightedSpending: TextView,
                                     val goToDetailButton: TextView) : OnChartValueSelectedListener {

        override fun onNothingSelected() {

        }

        override fun onValueSelected(entry: Entry?, highlight: Highlight?) {
            val currentSpendingModels = spendingModels[Math.round(entry!!.x)]
            highlightedSpending.setText(
                    currentSpendingModels.date + " " + currentSpendingModels.summarizedTotalAmount.toString()
            )

            goToDetailButton.setOnClickListener { }
        }
    }


}
