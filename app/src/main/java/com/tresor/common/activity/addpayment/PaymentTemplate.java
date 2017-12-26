package com.tresor.common.activity.addpayment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.tresor.R;
import com.tresor.common.activity.TresorPlainActivity;
import com.tresor.home.bottomsheet.IconAdapter;
import com.tresor.home.inteface.HomeActivityListener;
import com.tresor.home.model.FinancialHistoryModel;
import com.tresor.home.model.IconModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import faranjit.currency.edittext.CurrencyEditText;

/**
 * Created by kris on 10/15/17. Tokopedia
 */

public abstract class PaymentTemplate extends TresorPlainActivity implements PaymentTemplateInterface {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        setContentView(R.layout.add_new_data_activity);
        CurrencyEditText fieldAmount = (CurrencyEditText) findViewById(R.id.edit_text_insert_amount);
        EditText fieldInfo = (EditText) findViewById(R.id.edit_text_insert_info);
        TextView nextButton = (TextView) findViewById(R.id.next_button);
        RecyclerView iconList = (RecyclerView) findViewById(R.id.icon_list);
        FinancialHistoryModel model = initialModel();
        populateView(model, fieldAmount, fieldInfo);
        List<IconModel> generatedIcons = generatedIconList();
        generatedIcons.get(imageChoosen()).setChoosen(true);
        IconAdapter iconListAdapter = new IconAdapter(generatedIcons, model);
        iconList.setLayoutManager(new GridLayoutManager(this, 4));
        iconList.setAdapter(iconListAdapter);
        iconListAdapter.notifyDataSetChanged();
        nextButton.setOnClickListener(onFinishButtonClickedListener(fieldAmount, fieldInfo, model));
        fieldAmount.setOnKeyListener(onFieldAmountKeyListener(fieldInfo));
        fieldInfo.setOnKeyListener(onFieldInfoKeyListener(imm));
        fieldAmount.setLocale(new Locale("en_US"));
        fieldAmount.requestFocus();


    }

    protected abstract FinancialHistoryModel initialModel();

    protected abstract int imageChoosen();

    protected abstract int getMode();

    private void populateHasTagList(String info, List<String> hashTagList) {
        String patternString = "(\\s|\\A)#(\\w+)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher regexMatcher = pattern.matcher(info);
        while (regexMatcher.find()) {
            if (regexMatcher.group().length() != 0) {
                hashTagList.add(regexMatcher.group());
            }
        }
    }

    private View.OnClickListener onFinishButtonClickedListener(final CurrencyEditText fieldAmount,
                                                               final EditText fieldInfo,
                                                               final FinancialHistoryModel model) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO Start Activity For Result
                Intent intent = new Intent();
                intent.putExtra(
                        HomeActivityListener.EXTRA_ADD_DATA_RESULT,
                        resultModel(fieldInfo, fieldAmount, model)
                );
                setResult(getMode(), intent);
                finish();
            }
        };
    }

    private void populateView(FinancialHistoryModel model,
                              CurrencyEditText fieldAmount,
                              EditText fieldInfo) {
        if(model.getAmount() != null) {
            fieldAmount.setText(String.valueOf(model.getAmountUnformatted()));
            fieldInfo.setText(model.getInfo());
        }
    }

    private FinancialHistoryModel resultModel(EditText fieldInfo,
                                              CurrencyEditText fieldAmount,
                                              FinancialHistoryModel model) {
        String info = fieldInfo.getText().toString();
        List<String> hashTagList = new ArrayList<>();
        populateHasTagList(info, hashTagList);
        String price = fieldAmount.getText().toString();
        model.setInfo(info);
        model.setAmount(price);
        model.setHashtag(hashTagList);
        try {
            model.setAmountUnformatted(fieldAmount.getCurrencyDouble());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        model.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
        return model;
    }

    private List<IconModel> generatedIconList() {
        List<IconModel> iconModelList = new ArrayList<>();
        IconModel iconModel = new IconModel();
        iconModel.setIconImageId(0);
        iconModelList.add(iconModel);
        iconModel = new IconModel();
        iconModel.setIconImageId(1);
        iconModelList.add(iconModel);
        iconModel = new IconModel();
        iconModel.setIconImageId(2);
        iconModelList.add(iconModel);
        iconModel = new IconModel();
        iconModel.setIconImageId(3);
        iconModelList.add(iconModel);
        iconModel = new IconModel();
        iconModel.setIconImageId(4);
        iconModelList.add(iconModel);
        iconModel = new IconModel();
        iconModel.setIconImageId(5);
        iconModelList.add(iconModel);
        iconModel = new IconModel();
        iconModel.setIconImageId(6);
        iconModelList.add(iconModel);
        iconModel = new IconModel();
        iconModel.setIconImageId(7);
        iconModelList.add(iconModel);
        return iconModelList;
    }

    private View.OnKeyListener onFieldInfoKeyListener(final InputMethodManager imm) {
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        };
    }

    private View.OnKeyListener onFieldAmountKeyListener(final EditText fieldInfo) {
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            fieldInfo.setSelection(fieldInfo.getText().length());
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        };
    }
}
