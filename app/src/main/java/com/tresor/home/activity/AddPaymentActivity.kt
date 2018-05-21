package com.tresor.home.activity

import android.content.Context
import android.content.Intent
import com.tresor.common.activity.addpayment.PaymentTemplateKotlin
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.home.inteface.HomeActivityListener
import com.tresor.home.model.SpendingModelWrapper

import java.util.ArrayList

/**
 * Created by kris on 10/10/17. Tokopedia
 */

fun Context.addPaymentActivityIntent(): Intent {
    return Intent(this, AddPaymentActivity::class.java)
}

class AddPaymentActivity : PaymentTemplateKotlin() {
    override fun imageChosen(): Int {
        return INITIAL_IMAGE_SELECTOR_POSITION
    }

    val INITIAL_ITEM_POSITION = 0
    val INITIAL_IMAGE_SELECTOR_POSITION = 0

    override fun initialModel(): SpendingModelWrapper {
        return SpendingModelWrapper(INITIAL_ITEM_POSITION, generatePlainSpendingModel())
    }

    override fun getMode(): Int {
        return HomeActivityListener.ADD_NEW_PAYMENT_REQUEST_CODE
    }

    private fun generatePlainSpendingModel(): SpendingModel {
        return SpendingModel(
                0,
                "0",
                0.0,
                false,
                1,
                "",
                "",
                0,
                ArrayList(),
                ""
        )
    }
}
