package com.tresor.home.fragment

import com.tresor.common.model.viewmodel.SpendingListDatas
import com.tresor.common.model.viewmodel.SpendingModel

/**
 * Created by kris on 5/11/18. Tokopedia
 */
interface TodaySpendingInterface {

    fun onEmptySpending()

    fun renderSpending(spendingModelDatas: SpendingListDatas)

    fun addSpending(spendingModel: SpendingModel)

    fun editSpending(adapterIndex: Int, spendingModel: SpendingModel)

    fun deleteSpending(adapterIndex: Int, spendingModel: SpendingModel)

    fun addDataFromNextPage(nextPageSpendings: MutableList<SpendingModel>)

}