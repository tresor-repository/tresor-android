package com.tresor.statistic.totalspending.activity

import android.os.Bundle
import android.widget.EditText
import com.github.mikephil.charting.charts.LineChart
import com.tresor.R
import com.tresor.common.activity.DateSelectorActivity

class TotalSpendingActivity : DateSelectorActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_total_spending
    }

    override fun startDateEditText(): EditText {
        return findViewById(R.id.start_date_field) as EditText
    }

    override fun endDateEditText(): EditText {
        return findViewById(R.id.end_date_field) as EditText
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val hashTagLineChart = findViewById(R.id.hashtag_line_chart) as LineChart
        hashTagLineChart.apply {
            axisLeft.setDrawGridLines(false)
            axisLeft.setDrawLabels(false)
            xAxis.setDrawGridLines(false)
            legend.isEnabled = false
            setDrawGridBackground(false)
            axisRight.setDrawGridLines(false)
            description.textSize = 16f
        }

    }

}
