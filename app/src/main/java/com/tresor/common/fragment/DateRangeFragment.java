package com.tresor.common.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tresor.common.dialog.TresorTimePickerDialog;
import com.tresor.common.utils.DateEditor;

import java.util.Calendar;

/**
 * Created by kris on 10/18/17. Tokopedia
 */

public abstract class DateRangeFragment extends Fragment{

    private static final int TIME_PICKER_REQUEST_CODE = 5;

    private EditText startDateField;
    private EditText endDateField;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(getLayoutId(), container, false);
        startDateField = startDateEditText(mainView);
        startDateField.setOnClickListener(onStartDateClickedListener());
        endDateField = endDateEditText(mainView);
        endDateField.setOnClickListener(onEndDateClickedListener());
        initiateDateValue();
        initMainView(mainView);
        return mainView;
    }

    private void initiateDateValue() {
        String currentDate = String.valueOf(Calendar.getInstance().get(Calendar.DATE));
        String currentMonth = DateEditor
                .editMonth(getActivity(), Calendar.getInstance().get(Calendar.MONTH));
        String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        startDateField.setText(DateEditor
                .dayMonthNameYearFormatter("1", currentMonth, currentYear));
        endDateField.setText(DateEditor
                .dayMonthNameYearFormatter(currentDate, currentMonth, currentYear));
    }

    private View.OnClickListener onStartDateClickedListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TresorTimePickerDialog pickerDialogFragment = TresorTimePickerDialog
                        .openTimerDialog(TresorTimePickerDialog.START_DATE_MODE);
                pickerDialogFragment
                        .setTargetFragment(DateRangeFragment.this, TIME_PICKER_REQUEST_CODE);
                pickerDialogFragment.show(getFragmentManager(), "timePicker");
            }
        };
    }

    private View.OnClickListener onEndDateClickedListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TresorTimePickerDialog pickerDialogFragment = TresorTimePickerDialog
                        .openTimerDialog(TresorTimePickerDialog.END_DATE_MODE);
                pickerDialogFragment.show(getFragmentManager(), "timePicker");
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TresorTimePickerDialog.START_DATE_MODE:
                startDateChanged(getDate(data), getMonth(data), getYear(data));
                startDateField
                        .setText(retrievePickedDate(getDate(data), getMonth(data), getYear(data)));
                break;
            case TresorTimePickerDialog.END_DATE_MODE:
                endDateChanged(getDate(data), getMonth(data), getYear(data));
                endDateField
                        .setText(retrievePickedDate(getDate(data), getMonth(data), getYear(data)));
                break;
        }
    }

    private int getDate(Intent data) {
        return data.getIntExtra(TresorTimePickerDialog.EXTRAS_DATE,1);
    }

    private int getMonth(Intent data) {
        return data.getIntExtra(TresorTimePickerDialog.EXTRAS_MONTH, 1);
    }

    private int getYear(Intent data) {
        return data.getIntExtra(TresorTimePickerDialog.EXTRAS_YEAR, 2017);
    }

    private String retrievePickedDate(int date, int month, int year) {
        return DateEditor
                .dayMonthNameYearFormatter(String.valueOf(date),
                        String.valueOf(month),
                        String.valueOf(year));
    }

    protected abstract void initMainView(View mainView);

    protected abstract int getLayoutId();

    protected abstract EditText startDateEditText(View view);

    protected abstract EditText endDateEditText(View view);

    protected abstract void startDateChanged(int date, int month, int year);

    protected abstract void endDateChanged(int date, int month, int year);
}
