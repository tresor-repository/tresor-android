package com.tresor.common.activity.addpayment

import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.home.bottomsheet.IconAdapterKotlin
import com.tresor.home.model.IconModel
import com.tresor.home.model.SpendingModelWrapper
import java.text.DateFormat
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Created by kris on 5/27/18. Tokopedia
 */
class PaymentTemplatePresenter(val view: PaymentTemplateInterface)
    : PaymentTemplatePresenterInterface {

    override fun addData(oldModelWrapper: SpendingModelWrapper,
                         iconListAdapter: IconAdapterKotlin) {
        requestAdd(oldModelWrapper, iconListAdapter)
    }

    override fun editData(oldModelWrapper: SpendingModelWrapper,
                          iconListAdapter: IconAdapterKotlin) {
        requestEdit(oldModelWrapper, iconListAdapter)
    }

    private fun requestAdd(oldModelWrapper: SpendingModelWrapper,
                         iconAdapter: IconAdapterKotlin) {

        //TODO Send Altered Model to Network
        //alteredModel(oldSpendingModel, iconAdapter)

        //TODO then call this on Callback
        //view.generateResultModel(SpendingModelWrapper(oldModelWrapper.adapterPosition, networkResult))


        view.generateResultModel(SpendingModelWrapper(
                oldModelWrapper.adapterPosition,
                alteredModel(oldModelWrapper.spendingModel, iconAdapter))
        )
    }

    private fun requestEdit(oldModelWrapper: SpendingModelWrapper,
                         iconAdapter: IconAdapterKotlin) {

        //TODO Send Altered Model to Network
        //alteredModel(oldSpendingModel, iconAdapter)

        //TODO then call this on Callback
        //view.generateResultModel(SpendingModelWrapper(oldModelWrapper.adapterPosition, networkResult))


        view.generateResultModel(SpendingModelWrapper(
                oldModelWrapper.adapterPosition,
                alteredModel(oldModelWrapper.spendingModel, iconAdapter))
        )
    }

    private fun alteredModel(spendingModel: SpendingModel,
                             iconAdapter: IconAdapterKotlin)
            : SpendingModel {
        val info = view.getTextInfo()
        val hashTagList: MutableList<String> = populateHashTagList(
                info,
                iconAdapter.getChosenIconDefaultHashTag()
        )
        var appendString = ""
        hashTagList.forEach { hashTag -> appendString += hashTag }
        val amountUnformatted: Double = view.getAmountUnformattedDouble()
        val price = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(amountUnformatted)
        val date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
        return SpendingModel(spendingModel.id,
                price,
                amountUnformatted,
                spendingModel.userComma,
                spendingModel.currencyId,
                appendString,
                date,
                iconAdapter.getChosenIcon(),
                hashTagList,
                info
        )
    }

    private fun populateHashTagList(info: String, defaultIconHashTag: String)
            : MutableList<String> {
        val hashTagList = mutableListOf<String>()
        val patternString = "(\\s|\\A)#(\\w+)"
        val pattern = Pattern.compile(patternString)
        val regexMatcher = pattern.matcher(info)
        while (regexMatcher.find()) {
            if (regexMatcher.group().isNotEmpty()) {
                hashTagList.add(regexMatcher.group().trim().replace("#", ""))
            }
        }
        when(hashTagList.size) {
            0 -> hashTagList.add(defaultIconHashTag)
        }
        return hashTagList
    }

    override fun generateIconList(): MutableList<IconModel> {
        val iconModelList = mutableListOf<IconModel>()
        iconModelList.add(IconModel(0, "Food", true, false))
        iconModelList.add(IconModel(1, "Clothing", true, false))
        iconModelList.add(IconModel(2, "Tools", true, false))
        iconModelList.add(IconModel(3, "Health", true, false))
        iconModelList.add(IconModel(4, "Grocery", true, false))
        iconModelList.add(IconModel(5, "Electronics", true, false))
        iconModelList.add(IconModel(6, "Hygiene", true, false))
        iconModelList.add(IconModel(7, "Transportation", true, false))
        iconModelList.add(IconModel(8, "Vehicle", true, false))
        iconModelList.add(IconModel(9, "Shopping", true, false))
        return iconModelList
    }

}