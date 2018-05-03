package com.tresor.home.activity;

import android.app.Activity;

import com.tresor.common.activity.addpayment.PaymentTemplate;
import com.tresor.common.model.viewmodel.SpendingModel;
import com.tresor.home.inteface.HomeActivityListener;
import com.tresor.home.model.FinancialHistoryModel;
import com.tresor.home.model.SpendingModelWrapper;

import java.util.ArrayList;

/**
 * Created by kris on 10/10/17. Tokopedia
 */

public class AddPaymentActivity extends PaymentTemplate {
    @Override
    protected SpendingModelWrapper initialModel() {
        return new SpendingModelWrapper(0, generatePlainSpendingModel());
    }

    @Override
    protected int imageChoosen() {
        return 0;
    }

    @Override
    protected int getMode() {
        return HomeActivityListener.ADD_NEW_PAYMENT_REQUEST_CODE;
    }

    private SpendingModel generatePlainSpendingModel() {
        return new SpendingModel(0,
                "0",
                0,
                false,
                1,
                "",
                "",
                0,
                new ArrayList<String>(),
                "");
    }
}
