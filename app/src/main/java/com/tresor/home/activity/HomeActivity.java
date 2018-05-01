package com.tresor.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tresor.R;
import com.tresor.common.TresorActivity;
import com.tresor.home.dialog.EditPaymentDialog;
import com.tresor.home.fragment.ListFinancialHistoryFragment;
import com.tresor.home.inteface.HomeActivityListener;
import com.tresor.home.inteface.NewDataAddedListener;
import com.tresor.home.model.FinancialHistoryModel;
import com.tresor.statistic.dialog.TimePickerDialogFragment;

/**
 * Created by kris on 5/27/17. Tokopedia
 */

public class HomeActivity extends TresorActivity
        implements HomeActivityListener, NewDataAddedListener,
        EditPaymentDialog.EditItemListener,
        TimePickerDialogFragment.DatePickerListener{

    private ListFinancialHistoryFragment listFinancialHistoryFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSelectedMenu(R.id.add_menu);
    }


    @Override
    public void onDataAdded(FinancialHistoryModel newData) {
        setSelectedMenu(R.id.add_menu);
        listFinancialHistoryFragment.onDataAdded(newData);
    }

    @Override
    public void onItemEdited() {
        listFinancialHistoryFragment.onItemEdited();
    }

    @Override
    public void onDateSelected(int mode, int year, int month, int dayOfMonth) {
        //setSelectedMenu(R.id.statistic_menu);
    }

}
