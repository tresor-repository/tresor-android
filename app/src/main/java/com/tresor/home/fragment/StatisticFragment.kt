package com.tresor.home.fragment

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tresor.R
import com.tresor.common.model.testmodel.TestModel
import com.tresor.statistic.hashtagusage.activity.HashTagUsageComparisonActivity
import com.tresor.statistic.spendingpiechart.HashtagPieChartActivity
import com.tresor.statistic.totalspending.activity.TotalSpendingActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_home_statistic_chooser.*

/**
 * Created by kris on 11/14/17. Tokopedia
 */

class StatisticFragment : Fragment() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    companion object {
        fun createInstance(): StatisticFragment {
            return StatisticFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
                R.layout.fragment_home_statistic_chooser,
                container,
                false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        totalSpendingMenu.setOnClickListener { onTotalSpendingMenuClicked() }
        spendingComparisonMenu.setOnClickListener { onComparisonChartClicked() }
        pieChartMenu.setOnClickListener { onPieChartMenuClicked() }
    }

    private fun onTotalSpendingMenuClicked() {
        startActivity(Intent(activity, TotalSpendingActivity::class.java))
    }

    /*fun onTotalSpendingMenuClicked() {
        val testService = TestAuthenticatedService()
        compositeDisposable.add(testService.getTestList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(this::handleResponse, this::handleError))


    }*/

    private fun onComparisonChartClicked() {
        startActivity(Intent(activity, HashTagUsageComparisonActivity::class.java))
    }

    private fun onPieChartMenuClicked() {
        startActivity(Intent(activity, HashtagPieChartActivity::class.java))
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
