package com.tresor.home.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.tresor.R
import com.tresor.common.model.testmodel.TestModel
import com.tresor.home.network.TestAuthenticatedService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by kris on 11/14/17. Tokopedia
 */

class StatisticFragment : Fragment() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    companion object {
        fun createInstance(): StatisticFragment {
            return StatisticFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

            val testService = TestAuthenticatedService()
            compositeDisposable.add(testService.getTestList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(this::handleResponse, this::handleError))

        }
    }

    fun onComparisonChartClicked(): View.OnClickListener {
        return View.OnClickListener { }
    }

    fun onPieChartMenuClicked(): View.OnClickListener {
        return View.OnClickListener { }
    }

    private fun handleResponse(androidList: List<TestModel>) {

        val mAndroidArrayList = ArrayList(androidList)
    }

    private fun handleError(error: Throwable) {


    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }
}
