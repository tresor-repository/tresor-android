package com.tresor.common.activity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.tresor.common.dialog.TresorTimePickerDialog
import com.tresor.common.dialog.TresorTimePickerDialog.END_DATE_MODE
import com.tresor.common.dialog.TresorTimePickerDialog.START_DATE_MODE

import kotlinx.android.synthetic.main.activity_date_selector.*

abstract class DateSelectorActivity : TresorPlainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        setSupportActionBar(toolbar)


        startDateEditText().setOnClickListener(onStartDateClickedListener())
        endDateEditText().setOnClickListener(onEndDateClickedListener())
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun startDateEditText(): EditText

    protected abstract fun endDateEditText(): EditText

    private fun onStartDateClickedListener(): View.OnClickListener {
        return View.OnClickListener {
            showDatePickerDialog(START_DATE_MODE)
        }
    }

    private fun onEndDateClickedListener(): View.OnClickListener {
        return View.OnClickListener {
            showDatePickerDialog(END_DATE_MODE)
        }
    }

    private fun showDatePickerDialog(dateMode : Int) {
        TresorTimePickerDialog.openTimerDialog(dateMode).show(fragmentManager, localClassName)
    }
}
