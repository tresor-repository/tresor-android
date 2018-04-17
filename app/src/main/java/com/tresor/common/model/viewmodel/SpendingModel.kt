package com.tresor.common.model.viewmodel

/**
 * Created by kris on 12/30/17. Tokopedia
 */
data class SpendingModel(val id: Int,
                         val amount: String,
                         val amountUnformatted: Double,
                         val userComma: Boolean,
                         val currencyId: Int,
                         val hashTagString: String,
                         val date:String,
                         val theme:Int,
                         val listHashTag: List<String>)