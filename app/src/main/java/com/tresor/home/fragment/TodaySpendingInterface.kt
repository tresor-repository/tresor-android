package com.tresor.home.fragment

import com.tresor.common.model.viewmodel.SpendingModel

/**
 * Created by kris on 5/11/18. Tokopedia
 */
interface TodaySpendingInterface {

    fun onEmptySpending()

    fun renderSpending(spendingModelList: MutableList<SpendingModel>)

}