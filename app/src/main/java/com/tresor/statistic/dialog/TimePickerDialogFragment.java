package com.tresor.statistic.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by kris on 9/7/17. Tokopedia
 */

public class TimePickerDialogFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{

    private static final String TIMER_MODE_KEY = "timer_mode";
    public static final int START_DATE_MODE = 1;
    public static final int END_DATE_MODE = 2;

    private DatePickerListener listener;

    public static TimePickerDialogFragment openTimerDialog(int mode) {
        TimePickerDialogFragment dialog = new TimePickerDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TIMER_MODE_KEY, mode);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (DatePickerListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (DatePickerListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        listener.onDateSelected(getArguments().getInt(TIMER_MODE_KEY), year, month, dayOfMonth);
        dismiss();
    }

    public interface DatePickerListener {
        void onDateSelected(int mode, int year, int month, int dayOfMonth);
    }
}
