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
import com.tresor.home.viewholder.IconAdapterViewHolder
import kotlinx.android.synthetic.main.add_new_data_activity.*
import java.text.DateFormat
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Created by kris on 5/4/18. Tokopedia
 */
abstract class PaymentTemplateKotlin :
        PaymentTemplateInterface,
        TresorPlainActivity(),
        IconAdapterViewHolder.IconViewHolderListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_data_activity)
        val presenter = PaymentTemplatePresenter(this)

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE)
        (imm as InputMethodManager).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        populateView()
        val generatedIcons = presenter.generateIconList()
        val iconListAdapter = IconAdapterKotlin(generatedIcons, this)

        //TODO Figure out better method later?
        generatedIcons[imageChosen()].isChosen = true
        selected_category.text = generatedIcons[imageChosen()].defaultTag
        //TODO this only how to relate theme with chosen icon

        icon_list.apply {
            layoutManager = GridLayoutManager(this@PaymentTemplateKotlin, 4)
            adapter = iconListAdapter
        }
        iconListAdapter.notifyDataSetChanged()
        when(getMode()) {
            HomeActivityListener.EDIT_PAYMENT_REQUEST_CODE ->
                    next_button.setOnClickListener {
                        presenter.editData(initialModel(), iconListAdapter)
                    }
            else -> next_button.setOnClickListener {
                presenter.addData(initialModel(), iconListAdapter)
            }
        }
        edit_text_insert_amount.setOnKeyListener {
            _, keyCode, event ->  onFieldAmountKeyListener(keyCode, event)
        }
        edit_text_insert_info.setOnKeyListener {
            view, keyCode, event ->  onFieldInfoKeyListener(view, keyCode, event, imm)
        }
        //TODO set Locale depends on user setting
        edit_text_insert_amount.locale = Locale("in_ID")
        edit_text_insert_amount.requestFocus()
    }

    override fun iconClicked(chosenCategory: String) {
        icon_list.adapter.notifyDataSetChanged()
        selected_category.text = chosenCategory
    }

    private fun populateView() {
        val floatedAmountUnformatted = initialModel().spendingModel.amountUnformatted.toFloat()
        edit_text_insert_amount.setText(String.format("%.2f", floatedAmountUnformatted))
        edit_text_insert_info.setText(initialModel().spendingModel.info)
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

    override fun getTextInfo(): String {
        return edit_text_insert_info.text.toString()
    }

    override fun getAmountUnformattedDouble(): Double {
        return edit_text_insert_amount.currencyDouble
    }

    override fun getIconAdapter(): IconAdapterKotlin {
        return icon_list.adapter as IconAdapterKotlin
    }

    override fun generateResultModel(newModelWrapper: SpendingModelWrapper) {
        val intent = Intent()
        intent.putExtra(HomeActivityListener.EXTRA_ADD_DATA_RESULT, newModelWrapper)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE)
        (imm as InputMethodManager).toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    protected abstract fun initialModel(): SpendingModelWrapper

    protected abstract fun imageChosen(): Int

    protected abstract fun getMode(): Int

}