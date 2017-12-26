package com.tresor.home.bottomsheet;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tresor.R;
import com.tresor.home.inteface.IconSelectetionListener;
import com.tresor.home.inteface.NewDataAddedListener;
import com.tresor.home.model.FinancialHistoryModel;
import com.tresor.home.model.IconModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kris on 7/4/17. Tokopedia
 */

public class AddPaymentBottomSheet extends BottomSheetDialog implements IconSelectetionListener{

    private LinearLayout amountLayout;
    private LinearLayout iconLayout;
    private LinearLayout infoLayout;
    private LinearLayout stepByStepLayout;
    private RecyclerView iconList;
    private RecyclerView.Adapter iconListAdapter;
    private TextView textCurrency;
    private EditText fieldAmount;
    private EditText fieldInfo;
    private Button finishButton;
    private Button nextButton;
    private Button skipButton;
    private View showAllChevron;
    private NewDataAddedListener listener;
    private int selectedIconIndex = 0;
    private int iconId = 0;
    private List<IconModel> generatedIcons;

    public AddPaymentBottomSheet(@NonNull Context context, NewDataAddedListener listener) {
        super(context);
        this.listener = listener;
        initView(context);
    }

    private void initView(Context context) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.add_new_data_bottom_sheet, null);
        setContentView(view);

        amountLayout = (LinearLayout) view.findViewById(R.id.amount_layout);
        infoLayout = (LinearLayout) view.findViewById(R.id.edit_info_layout);
        iconLayout = (LinearLayout) view.findViewById(R.id.icon_layout);
        stepByStepLayout = (LinearLayout) view.findViewById(R.id.step_by_step_layout);
        textCurrency = (TextView) view.findViewById(R.id.currency);
        fieldAmount = (EditText) view.findViewById(R.id.edit_text_insert_amount);
        fieldInfo = (EditText) view.findViewById(R.id.edit_text_insert_info);
        finishButton = (Button) view.findViewById(R.id.finish_button);
        nextButton = (Button) view.findViewById(R.id.next_button);
        skipButton = (Button) view.findViewById(R.id.skip_button);
        iconList = (RecyclerView) view.findViewById(R.id.icon_list);
        generatedIcons = generatedIconList();
        iconListAdapter = new IconAdapter(generatedIcons, this);
        iconList.setLayoutManager(new GridLayoutManager(context, 4));
        iconList.setAdapter(iconListAdapter);
        nextButton.setOnClickListener(onNextButtonListener());
        skipButton.setOnClickListener(onSkipButtonClickedListener());
        finishButton.setOnClickListener(onFinishButtonClickedListener());
    }

    private View.OnClickListener onNextButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!infoLayout.isShown()) infoLayout.setVisibility(View.VISIBLE);
                else {
                    iconList.setVisibility(View.VISIBLE);
                    finishButton.setVisibility(View.VISIBLE);
                    stepByStepLayout.setVisibility(View.GONE);
                }
            }
        };
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
            if(regexMatcher.group().length() != 0) {
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
        this.iconId =iconId;
        iconListAdapter.notifyDataSetChanged();
    }

    private void refreshIcons() {
        for(int i = 0; i < generatedIcons.size(); i++) {
            generatedIcons.get(i).setChoosen(false);
        }
    }
}
