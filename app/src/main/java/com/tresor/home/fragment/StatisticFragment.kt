package com.tresor.home.fragment

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.tresor.R
import com.tresor.statistic.totalspending.activity.TotalSpendingActivity

/**
 * Created by kris on 11/14/17. Tokopedia
 */

class StatisticFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        val view = inflater.inflate(R.layout.fragment_home_statistic_chooser, container, false)
        val totalSpendingMenu = view.findViewById(R.id.total_spending_menu) as ImageView
        val spendingComparisonMenu = view.findViewById(R.id.spending_comparison_menu) as ImageView
        val pieChartMenu = view.findViewById(R.id.pie_chart_menu) as ImageView

        totalSpendingMenu.setOnClickListener(onTotalSpendingMenuClicked())
        spendingComparisonMenu.setOnClickListener(onComparisonChartClicked())

        return view
    }

    fun onTotalSpendingMenuClicked(): View.OnClickListener {
        return View.OnClickListener {
            startActivity(Intent(activity, TotalSpendingActivity::class.java))
        }
    }

    fun onComparisonChartClicked(): View.OnClickListener {
        return View.OnClickListener {  }
    }

    fun onPieChartMenuClicked(): View.OnClickListener {
        return View.OnClickListener {  }
    }
}
