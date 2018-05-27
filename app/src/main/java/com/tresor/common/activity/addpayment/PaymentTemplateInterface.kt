package com.tresor.common.activity.addpayment

import com.tresor.home.bottomsheet.IconAdapterKotlin
import com.tresor.home.model.SpendingModelWrapper

/**
 * Created by kris on 5/27/18. Tokopedia
 */
interface PaymentTemplateInterface {

    companion object {
        val EXTRAS_OPEN_EDIT_PAYMENT_PAGE = "EXTRAS_OPEN_EDIT_PAYMENT_PAGE"
    }

    fun getTextInfo(): String

    fun getAmountUnformattedDouble(): Double

    fun getIconAdapter(): IconAdapterKotlin

    fun generateResultModel(newModelWrapper: SpendingModelWrapper)

}