package com.tresor.home.activity;

import com.tresor.common.activity.addpayment.PaymentTemplate;
import com.tresor.common.model.viewmodel.SpendingModel;
import com.tresor.home.inteface.HomeActivityListener;
import com.tresor.home.model.FinancialHistoryModel;
import com.tresor.home.model.SpendingModelWrapper;

/**
 * Created by kris on 10/15/17. Tokopedia
 */

public class EditPaymentActivity extends PaymentTemplate{
    @Override
    protected SpendingModelWrapper initialModel() {
        return getModel();
    }

    @Override
    protected int imageChoosen() {
        return getModel().getSpendingModel().getTheme();
    }

    @Override
    protected int getMode() {
        return HomeActivityListener.EDIT_PAYMENT_REQUEST_CODE;
    }

    private SpendingModelWrapper getModel() {
        return getIntent().getParcelableExtra(EXTRAS_OPEN_EDIT_PAYMENT_PAGE);
    }
}
