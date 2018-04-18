package com.tresor.statistic.spendingpiechart

import android.os.Bundle
import android.widget.EditText
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.tresor.R
import com.tresor.common.activity.DateSelectorActivity
import com.tresor.common.model.viewmodel.HashtagUsageModel
import kotlinx.android.synthetic.main.date_selector_header.*
import kotlinx.android.synthetic.main.activity_hash_tag_pie_chart.*
/**
 * Created by kris on 4/16/18. Tokopedia
 */

class HashtagPieChartActivity : DateSelectorActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_hash_tag_pie_chart
    }

    override fun startDateEditText(): EditText {
        return startDateField
    }

    override fun endDateEditText(): EditText {
        return endDateField
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO change setChartData after receive data from network
        setChartData(dummyHashTagStatistic())
    }

    private fun setChartData(hashTagUsages : List<HashtagUsageModel>) {
        val dataSet = PieDataSet(getPercentageAmount(hashTagUsages), "Percentage of Hashtags")
        dataSet.colors = (colorList())
        hashtagPieChart.apply {
            description.isEnabled = false
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            data = PieData(dataSet)
            animate()
        }
    }

    private fun getPercentageAmount(hashTagUsages : List<HashtagUsageModel>) : List<PieEntry> {
        val pieEntries : ArrayList<PieEntry> = arrayListOf()
        val totalHashTagUsage = getTotalAmount(hashTagUsages)
        for (hashTagUsage in hashTagUsages) {
            val percentile : Float = hashTagUsage.timesMentioned/ totalHashTagUsage
            pieEntries.add(PieEntry(percentile, hashTagUsage.name))
        }
        return pieEntries
    }

    private fun getTotalAmount(hashTagUsages : List<HashtagUsageModel>) : Float {
        var amount = 0f
        for (hashtagUsage in hashTagUsages) amount += hashtagUsage.timesMentioned
        return amount
    }

    private fun colorList() : List<Int> {
        val listColor : ArrayList<Int> = arrayListOf()
        listColor.add(resources.getColor(R.color.pie1))
        listColor.add(resources.getColor(R.color.pie2))
        listColor.add(resources.getColor(R.color.pie3))
        listColor.add(resources.getColor(R.color.pie4))
        listColor.add(resources.getColor(R.color.pie5))
        listColor.add(resources.getColor(R.color.pie6))
        return listColor
    }

    private fun dummyHashTagStatistic() : List<HashtagUsageModel> {
        val hashtagUsageModel : ArrayList<HashtagUsageModel> = arrayListOf()
        hashtagUsageModel.add(HashtagUsageModel("#makan", 22f))
        hashtagUsageModel.add(HashtagUsageModel("#malem", 34f))
        hashtagUsageModel.add(HashtagUsageModel("#KFC", 12f))
        hashtagUsageModel.add(HashtagUsageModel("#bolang", 18f))
        hashtagUsageModel.add(HashtagUsageModel("#pijet", 18f))
        return hashtagUsageModel
    }

}