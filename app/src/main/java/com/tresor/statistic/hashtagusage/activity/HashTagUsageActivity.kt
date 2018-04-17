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

class HashTagUsageActivity
    : DateSelectorActivity(), HashTagUsageDialog.HashTagUsageDialogListener {

    override fun onFinishChoosingSpendingDialog(hashTagList: List<HashtagListModel>) {
        setChartData(hashTagList)
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
                .createDialog(hashtagList as ArrayList<HashtagListModel>)
        analyzeDialog.show(ft, "dialog")
    }

    private fun multipleHashtagLine(hashTags: List<HashtagListModel>)
            : List<ILineDataSet> {
        val lineDataSets = ArrayList<ILineDataSet>()
        for (i in hashTags.indices) {
            val lineDataSet = LineDataSet(getLineAmountHashTag(hashTags),
                    hashTags[i].hashtag)
            setDatasetMode(lineDataSet, setColor(i))
            lineDataSets.add(lineDataSet)
        }
        return lineDataSets
    }

    private fun getLineAmountHashTag(hashtags: List<HashtagListModel>) : List<Entry> {
        val lineEntries : ArrayList<Entry> = arrayListOf()
        for (i in hashtags.indices) {
            if (i > 0) lineEntries.add(
                    Entry(
                            i.toFloat(),
                            hashtags[i].amountUnformatted.toFloat()
                                    + hashtags[i - 1].amountUnformatted.toFloat())
            ) else lineEntries.add(Entry(
                    i.toFloat(),
                    hashtags[i].amountUnformatted.toFloat())
            )
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
        while (index < 2) {
            spendingModelList.add(HashtagListModel(
                    "1234",
                    dummyHashTag[index],
                    Math.random() * 10000,
                    "20000"
            ))
            index++
        }
        return spendingModelList
    }

}