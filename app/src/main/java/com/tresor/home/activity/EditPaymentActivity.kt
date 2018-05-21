package com.tresor.home.activity

import android.content.Context
import android.content.Intent
import com.tresor.common.activity.addpayment.PaymentTemplateInterface
import com.tresor.common.activity.addpayment.PaymentTemplateKotlin
import com.tresor.home.inteface.HomeActivityListener
import com.tresor.home.model.SpendingModelWrapper

/**
 * Created by kris on 10/15/17. Tokopedia
 */

fun Context.editPaymentActivityIntent(spendingModelWrapper: SpendingModelWrapper): Intent {
    return Intent(this, EditPaymentActivity::class.java).apply {
        putExtra(PaymentTemplateInterface.EXTRAS_OPEN_EDIT_PAYMENT_PAGE, spendingModelWrapper)
    }
}

class EditPaymentActivity : PaymentTemplateKotlin() {

    private val model: SpendingModelWrapper
        get() = intent.getParcelableExtra(PaymentTemplateInterface.EXTRAS_OPEN_EDIT_PAYMENT_PAGE)

    override fun initialModel(): SpendingModelWrapper {
        return model
    }

    override fun imageChosen(): Int {
        return model.spendingModel.theme
    }

    override fun getMode(): Int {
        return HomeActivityListener.EDIT_PAYMENT_REQUEST_CODE
    }
}
