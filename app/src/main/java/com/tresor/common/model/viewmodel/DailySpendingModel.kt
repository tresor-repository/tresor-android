package com.tresor.common.model.viewmodel

/**
 * Created by kris on 4/22/18. Tokopedia
 */
data class DailySpendingModel(val id: String, val date: String, val listOfSpending: List<SpendingModel>)
{

    var totalAmount : Float = 0f

}