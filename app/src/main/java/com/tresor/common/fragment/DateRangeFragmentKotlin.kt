package com.tresor.common.fragment

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.tresor.common.dialog.TresorTimePickerDialog
import com.tresor.common.utils.DateEditor
import java.util.*

/**
 * Created by kris on 5/8/18. Tokopedia
 */
abstract class DateRangeFragmentKotlin: Fragment() {

    private val TIME_PICKER_REQUEST_CODE = 5

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMainView()
        initiateDateValue()
    }

    private fun initiateDateValue() {
        val currentDate = Calendar.getInstance().get(Calendar.DATE).toString()
        val currentMonth = DateEditor
                .editMonth(activity, Calendar.getInstance().get(Calendar.MONTH))
        val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
        startDateEditText().setText(DateEditor
                .dayMonthNameYearFormatter("1", currentMonth, currentYear))
        endDateEditText().setText(DateEditor
                .dayMonthNameYearFormatter(currentDate, currentMonth, currentYear))
        startDateEditText().setOnClickListener { onStartDateClickedListener() }
        endDateEditText().setOnClickListener{onEndDateClickedListener()}
    }

    private fun onStartDateClickedListener() {
        val pickerDialogFragment = TresorTimePickerDialog
                .openTimerDialog(TresorTimePickerDialog.START_DATE_MODE)
        pickerDialogFragment
                .setTargetFragment(this@DateRangeFragmentKotlin, TIME_PICKER_REQUEST_CODE)
        pickerDialogFragment.show(fragmentManager, "timePicker")
    }

    private fun onEndDateClickedListener() {
        val pickerDialogFragment = TresorTimePickerDialog
                .openTimerDialog(TresorTimePickerDialog.END_DATE_MODE)
        pickerDialogFragment.show(fragmentManager, "timePicker")
    }

    protected abstract fun initMainView()

    protected abstract fun getLayoutId(): Int

    protected abstract fun startDateEditText(): EditText

    protected abstract fun endDateEditText(): EditText

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TresorTimePickerDialog.START_DATE_MODE -> {
                startDateChanged(getDate(data!!), getMonth(data), getYear(data))
                startDateEditText()
                        .setText(retrievePickedDate(getDate(data), getMonth(data), getYear(data)))
            }
            TresorTimePickerDialog.END_DATE_MODE -> {
                endDateChanged(getDate(data!!), getMonth(data), getYear(data))
                endDateEditText()
                        .setText(retrievePickedDate(getDate(data), getMonth(data), getYear(data)))
            }
        }
    }

    private fun getDate(data: Intent): Int {
        return data.getIntExtra(TresorTimePickerDialog.EXTRAS_DATE, 1)
    }

    private fun getMonth(data: Intent): Int {
        return data.getIntExtra(TresorTimePickerDialog.EXTRAS_MONTH, 1)
    }

    private fun getYear(data: Intent): Int {
        return data.getIntExtra(TresorTimePickerDialog.EXTRAS_YEAR, 2017)
    }

    private fun retrievePickedDate(date: Int, month: Int, year: Int): String {
        return DateEditor
                .dayMonthNameYearFormatter(date.toString(),
                        month.toString(),
                        year.toString())
    }

    protected abstract fun startDateChanged(date: Int, month: Int, year: Int)

    protected abstract fun endDateChanged(date: Int, month: Int, year: Int)

}