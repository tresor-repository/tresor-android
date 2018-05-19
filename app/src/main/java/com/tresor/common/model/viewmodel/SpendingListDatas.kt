package com.tresor.common.model.viewmodel

/**
 * Created by kris on 5/19/18. Tokopedia
 */
data class SpendingListDatas(val spendingModelList: MutableList<SpendingModel>,
                             var totalAmount: Double,
                             var count: Int)