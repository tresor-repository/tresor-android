package com.tresor.statistic.hashtagusage.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.tresor.R
import com.tresor.common.activity.DateSelectorActivity
import com.tresor.common.model.viewmodel.HashtagListModel
import com.tresor.statistic.hashtagusage.fragment.HashTagUsageDialog
import kotlinx.android.synthetic.main.activity_hash_tag_usage.*
import kotlinx.android.synthetic.main.date_selector_header.*
import java.util.ArrayList

/**
 * Created by kris on 3/31/18. Tokopedia
 */

class HashTagUsageComparisonActivity
    : DateSelectorActivity(), HashTagUsageDialog.HashTagUsageDialogListener {

    override fun onFinishChoosingSpendingDialog(hashTagList: List<String>) {
        //TODO
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_hash_tag_usage
    }

    override fun startDateEditText(): EditText {
        return startDateField
    }

    override fun endDateEditText(): EditText {
        return endDateField
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setChartData(dummyTotalSpendingModel())
    }

    private fun setChartData(spendingList: List<HashtagListModel>) {
        selectHastagButton.setOnClickListener{
            onHashTagButtonClickedListener(spendingList)
        }
        val lineData = LineData(multipleHashtagLine(spendingList))
        lineData.setDrawValues(false)
        hashtagMultiLineChart.data = lineData
        hashtagMultiLineChart.animateX(1000)
    }

    private fun onHashTagButtonClickedListener(hashtagList: List<HashtagListModel>) {
        val ft = fragmentManager.beginTransaction()
        val prev = fragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        val analyzeDialog = HashTagUsageDialog
                .createDialog(extractHashTagString(hashtagList))
        analyzeDialog.show(ft, "dialog")
    }

    private fun multipleHashtagLine(hashTags: List<HashtagListModel>)
            : List<ILineDataSet> {
        val lineDataSets = ArrayList<ILineDataSet>()
        for (i in hashTags.indices) {
            val lineDataSet = LineDataSet(getLineAmountHashTag(hashTags[i].amountUnformatted),
                    hashTags[i].hashtag)
            setDatasetMode(lineDataSet, setColor(i))
            lineDataSets.add(lineDataSet)
        }
        return lineDataSets
    }

    private fun getLineAmountHashTag(amountList: List<Double>) : List<Entry> {
        val lineEntries : ArrayList<Entry> = arrayListOf()
        var totalAmount = 0f
        for (i in amountList.indices) {
            totalAmount += amountList[i].toFloat()
            lineEntries.add(Entry(i.toFloat(), totalAmount))
        }
        return lineEntries
    }

    private fun setDatasetMode(set : LineDataSet, color : Int) {
        set.apply {
            setDrawCircles(true)
            setDrawFilled(false)
            fillColor = color
            lineWidth = 3.6f
            circleRadius = 4f
            setCircleColor(color)
            highLightColor = Color.rgb(244, 117, 117)
            setColor(color)
            fillAlpha = 100
            setDrawHorizontalHighlightIndicator(false)
            fillFormatter = IFillFormatter{
                dataSet: ILineDataSet?,
                dataProvider: LineDataProvider? ->  (-10).toFloat()
            }
        }
    }

    private fun setColor(position: Int) : Int {
        when(position) {
            0 -> return resources.getColor(R.color.pie1)
            1 -> return resources.getColor(R.color.pie2)
            2 -> return resources.getColor(R.color.pie3)
            3 -> return resources.getColor(R.color.pie4)
            4 -> return resources.getColor(R.color.pie5)
            else -> {return resources.getColor(R.color.pie1)}
        }
    }

    private fun dummyTotalSpendingModel(): List<HashtagListModel> {

        var index = 0

        val spendingModelList: ArrayList<HashtagListModel> = arrayListOf()

        val dummyHashTag: ArrayList<String> = arrayListOf()

        dummyHashTag.add("Makan")
        dummyHashTag.add("Minum")
        dummyHashTag.add("Boker")
        while (index < 3) {
            spendingModelList.add(HashtagListModel(
                    "1234",
                    dummyHashTag[index],
                    dummyValue(),
                    dummyFormattedValue()
            ))
            index++
        }
        return spendingModelList
    }

    private fun dummyValue(): List<Double> {
        var index = 0
        val list : MutableList<Double> = arrayListOf()
        while (index < 13) {
            list.add(Math.random() * 1000)
            index++
        }
        return list
    }

    private fun dummyFormattedValue(): List<String> {
        var index = 0
        val list : MutableList<String> = arrayListOf()
        while (index < 13) {
            list.add("20000")
            index++
        }
        return list
    }

    private fun extractHashTagString(analyzedHashTagList: List<HashtagListModel>) :
            ArrayList<String> {
        val hashTagList = arrayListOf<String>()
        for (hashTagModel : HashtagListModel in analyzedHashTagList) {
            hashTagList.add(hashTagModel.hashtag)
        }
        return hashTagList
    }

}