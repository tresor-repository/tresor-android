package com.tresor.common.activity.addpayment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.tresor.R
import com.tresor.common.activity.TresorPlainActivity
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.home.bottomsheet.IconAdapterKotlin
import com.tresor.home.inteface.HomeActivityListener
import com.tresor.home.model.IconModel
import com.tresor.home.model.SpendingModelWrapper
import kotlinx.android.synthetic.main.add_new_data_activity.*
import java.text.DateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Created by kris on 5/4/18. Tokopedia
 */
abstract class PaymentTemplateKotlin : TresorPlainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_data_activity)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE)
        (imm as InputMethodManager).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        populateView()
        val generatedIcons = generatedIconList()
        val iconListAdapter = IconAdapterKotlin(generatedIcons)
        generatedIcons[imageChosen()].isChosen = true
        icon_list.apply {
            layoutManager = GridLayoutManager(this@PaymentTemplateKotlin, 4)
            adapter = iconListAdapter
        }
        iconListAdapter.notifyDataSetChanged()
        when(getMode()) {
            HomeActivityListener.EDIT_PAYMENT_REQUEST_CODE ->
                    next_button.setOnClickListener { onFinishButtonClickedListener(
                            initialModel(),
                            iconListAdapter
                    ) }
            else -> next_button.setOnClickListener { onFinishButtonClickedListener(
                    initialModel(),
                    iconListAdapter) }
        }
        edit_text_insert_amount.setOnKeyListener {
            _, keyCode, event ->  onFieldAmountKeyListener(keyCode, event)
        }
        edit_text_insert_info.setOnKeyListener {
            view, keyCode, event ->  onFieldInfoKeyListener(view, keyCode, event, imm)
        }
        edit_text_insert_amount.locale = Locale("en_US")
        edit_text_insert_amount.requestFocus()
    }

    private fun populateView() {
        edit_text_insert_amount.setText((initialModel().spendingModel.amountUnformatted).toString())
        edit_text_insert_info.setText(initialModel().spendingModel.info)
    }

    private fun onFinishButtonClickedListener(modelWrapper: SpendingModelWrapper,
                                              iconAdapter: IconAdapterKotlin) {
        //TODO Call after successfully add or edit
        val intent = Intent()
        intent.putExtra(HomeActivityListener.EXTRA_ADD_DATA_RESULT, resultModel(
                modelWrapper,
                iconAdapter))
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE)
        (imm as InputMethodManager).toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun resultModel(modelWrapper: SpendingModelWrapper,
                            iconAdapter: IconAdapterKotlin): SpendingModelWrapper{
        return SpendingModelWrapper(
                modelWrapper.adapterPosition,
                alteredModel(modelWrapper.spendingModel, iconAdapter))
    }

    private fun alteredModel(spendingModel: SpendingModel,
                             iconAdapter: IconAdapterKotlin): SpendingModel {
        val info = edit_text_insert_info.text.toString()
        val hashTagList: MutableList<String> = populateHashTagList(
                info,
                iconAdapter.getChosenIconDefaultHashTag()
        )
        var appendString = ""
        hashTagList.forEach { hashTag -> appendString += hashTag }
        val amountUnformatted: Double = edit_text_insert_amount.currencyDouble
        val price = amountUnformatted.toString()
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

    private fun onFieldAmountKeyListener(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_ENTER -> {
                    edit_text_insert_info.setSelection(edit_text_insert_info.getText().length)
                    return true
                }
                else -> {
                }
            }
        }
        return false
    }

    private fun onFieldInfoKeyListener(v: View,
                                       keyCode: Int,
                                       event: KeyEvent,
                                       imm: InputMethodManager): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_ENTER -> {
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    return true
                }
                else -> {
                }
            }
        }
        return false
    }

    protected abstract fun initialModel(): SpendingModelWrapper

    protected abstract fun imageChosen(): Int

    protected abstract fun getMode(): Int

    private fun generatedIconList(): MutableList<IconModel> {
        val iconModelList = mutableListOf<IconModel>()
        iconModelList.add(IconModel(0, "GeneralSpending", true, false))
        iconModelList.add(IconModel(1, "Food", true, false))
        iconModelList.add(IconModel(2, "Clothing", true, false))
        iconModelList.add(IconModel(3, "Tools", true, false))
        iconModelList.add(IconModel(4, "Health", true, false))
        iconModelList.add(IconModel(5, "Grocery", true, false))
        iconModelList.add(IconModel(6, "Electronics", true, false))
        iconModelList.add(IconModel(7, "Hygiene", true, false))
        iconModelList.add(IconModel(8, "Transportation", true, false))
        return iconModelList
    }

}