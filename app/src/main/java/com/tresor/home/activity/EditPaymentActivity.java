package com.tresor.home.activity;

import com.tresor.common.activity.addpayment.PaymentTemplate;
import com.tresor.home.inteface.HomeActivityListener;
import com.tresor.home.model.FinancialHistoryModel;

/**
 * Created by kris on 10/15/17. Tokopedia
 */

public class EditPaymentActivity extends PaymentTemplate{
    @Override
    protected FinancialHistoryModel initialModel() {
        return getModel();
    }

    @Override
    protected int imageChoosen() {
        return getModel().getTheme();
    }

    @Override
    protected int getMode() {
        return HomeActivityListener.EDIT_PAYMENT_REQUEST_CODE;
    }

    private FinancialHistoryModel getModel() {
        return getIntent().getParcelableExtra(EXTRAS_OPEN_EDIT_PAYMENT_PAGE);
    }
}
