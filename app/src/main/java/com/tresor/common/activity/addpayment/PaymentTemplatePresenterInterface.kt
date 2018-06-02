package com.tresor.common.activity.addpayment

import com.tresor.home.bottomsheet.IconAdapterKotlin
import com.tresor.home.model.IconModel
import com.tresor.home.model.SpendingModelWrapper

/**
 * Created by kris on 5/27/18. Tokopedia
 */

interface PaymentTemplatePresenterInterface {

    fun addData(oldModelWrapper: SpendingModelWrapper, iconListAdapter: IconAdapterKotlin)

    fun editData(oldModelWrapper: SpendingModelWrapper, iconListAdapter: IconAdapterKotlin)

    fun generateIconList(): MutableList<IconModel>

}
