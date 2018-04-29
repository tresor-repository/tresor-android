package com.tresor.statistic.totalspending.adapter

import android.content.Context
import android.widget.ArrayAdapter
import com.tresor.R

/**
 * Created by kris on 4/27/18. Tokopedia
 */
class SortingSpinnerAdapter(context: Context)
    : ArrayAdapter<String>(context, R.layout.tresor_spinner_adapter) {

    private val arrayOfOptions : List<String> = listOf(
            "Sort By Highest Spending",
            "Sort by Lowest Spending",
            "Sort by Date"
    )

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): String {
        return arrayOfOptions[position]
    }

}