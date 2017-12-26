package com.tresor.home.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tresor.R;
import com.tresor.common.dialog.NoTitleDialogFragment;
import com.tresor.home.bottomsheet.IconAdapter;
import com.tresor.home.inteface.IconSelectetionListener;
import com.tresor.home.inteface.NewDataAddedListener;
import com.tresor.home.model.FinancialHistoryModel;
import com.tresor.home.model.IconModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import faranjit.currency.edittext.CurrencyEditText;

/**
 * Created by kris on 7/16/17. Tokopedia
 */

public class AddPaymentDialog extends NoTitleDialogFragment implements IconSelectetionListener {

    private LinearLayout amountLayout;
    private LinearLayout iconLayout;
    private LinearLayout infoLayout;
    private LinearLayout stepByStepLayout;
    private RecyclerView iconList;
    private RecyclerView.Adapter iconListAdapter;
    private TextView textCurrency;
    private CurrencyEditText fieldAmount;
    private EditText fieldInfo;
    private TextView finishButton;
    private View showAllChevron;
    private NewDataAddedListener listener;
    private int selectedIconIndex = 0;
    private int iconId = 0;
    private List<IconModel> generatedIcons;
    private InputMethodManager imm;

    public AddPaymentDialog() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (NewDataAddedListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (NewDataAddedListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.add_new_data_bottom_sheet, container);
        amountLayout = (LinearLayout) view.findViewById(R.id.amount_layout);
        infoLayout = (LinearLayout) view.findViewById(R.id.edit_info_layout);
        iconLayout = (LinearLayout) view.findViewById(R.id.icon_layout);
        stepByStepLayout = (LinearLayout) view.findViewById(R.id.step_by_step_layout);
        textCurrency = (TextView) view.findViewById(R.id.currency);
        fieldAmount = (CurrencyEditText) view.findViewById(R.id.edit_text_insert_amount);
        fieldInfo = (EditText) view.findViewById(R.id.edit_text_insert_info);
        finishButton = (TextView) view.findViewById(R.id.finish_button);
        TextView nextButton = (TextView) view.findViewById(R.id.next_button);
        TextView skipButton = (TextView) view.findViewById(R.id.skip_button);
        iconList = (RecyclerView) view.findViewById(R.id.icon_list);
        generatedIcons = generatedIconList();
        iconListAdapter = new IconAdapter(generatedIcons, this);
        iconList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        iconList.setAdapter(iconListAdapter);
        nextButton.setOnClickListener(onNextButtonListener());
        skipButton.setOnClickListener(onSkipButtonClickedListener());
        finishButton.setOnClickListener(onFinishButtonClickedListener());
        fieldAmount.setOnKeyListener(onFieldAmountKeyListener());
        fieldInfo.setOnKeyListener(onFieldInfoKeyListener());
        fieldAmount.setLocale(new Locale("en_US"));
        fieldAmount.requestFocus();
        imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        return view;
    }

    private View.OnClickListener onNextButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();
            }
        };
    }

    private void nextStep() {
        if (!infoLayout.isShown()) infoLayout.setVisibility(View.VISIBLE);
        else {
            iconList.setVisibility(View.VISIBLE);
            finishButton.setVisibility(View.VISIBLE);
            stepByStepLayout.setVisibility(View.GONE);
        }
    }

    private View.OnClickListener onSkipButtonClickedListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoLayout.setVisibility(View.VISIBLE);
                iconList.setVisibility(View.VISIBLE);
                stepByStepLayout.setVisibility(View.GONE);
                finishButton.setVisibility(View.VISIBLE);
            }
        };
    }

    private View.OnClickListener onFinishButtonClickedListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinancialHistoryModel model = new FinancialHistoryModel();
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
                model.setTheme(iconId);
                listener.onDataAdded(model);
                dismiss();
            }
        };
    }

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

    @Override
    public void onIconClicked(int position, int iconId) {
        refreshIcons();
        generatedIcons.get(position).setChoosen(true);
        selectedIconIndex = position;
        this.iconId = iconId;
        iconListAdapter.notifyDataSetChanged();
    }

    private void refreshIcons() {
        for (int i = 0; i < generatedIcons.size(); i++) {
            generatedIcons.get(i).setChoosen(false);
        }
    }

    private View.OnKeyListener onFieldInfoKeyListener() {
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            nextStep();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        };
    }

    private View.OnKeyListener onFieldAmountKeyListener() {
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            if(infoLayout.isShown() && !fieldInfo.isSelected()) {
                                infoLayout.requestFocus();
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                            } else if(fieldInfo.isSelected()) {
                              nextStep();
                            } else {
                                nextStep();
                                infoLayout.requestFocus();
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
