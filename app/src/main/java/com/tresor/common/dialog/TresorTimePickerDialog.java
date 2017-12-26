package com.tresor.common.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by kris on 10/18/17. Tokopedia
 */

public class TresorTimePickerDialog extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{

    public static final String TIMER_MODE_KEY = "TIMER_MODE_KEY";
    public static final String EXTRAS_YEAR = "EXTRAS_YEAR";
    public static final String EXTRAS_MONTH = "EXTRAS_MONTH";
    public static final String EXTRAS_DATE = "EXTRAS_DATE";
    public static final int START_DATE_MODE = 8;
    public static final int END_DATE_MODE = 9;

    public static TresorTimePickerDialog openTimerDialog(int mode) {
        TresorTimePickerDialog dialog = new TresorTimePickerDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(TIMER_MODE_KEY, mode);
        dialog.setArguments(bundle);
        return dialog;
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
        Intent intent = new Intent();
        intent.putExtra(EXTRAS_YEAR, year);
        intent.putExtra(EXTRAS_MONTH, month);
        intent.putExtra(EXTRAS_DATE, dayOfMonth);
        getTargetFragment()
                .onActivityResult(getArguments()
                .getInt(TIMER_MODE_KEY), Activity.RESULT_OK, intent);
        dismiss();
    }
}
