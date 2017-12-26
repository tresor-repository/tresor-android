package com.tresor.home.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.tresor.R;
import com.tresor.common.dialog.NoTitleDialogFragment;
import com.tresor.home.bottomsheet.IconAdapter;
import com.tresor.home.inteface.IconSelectetionListener;
import com.tresor.home.model.FinancialHistoryModel;
import com.tresor.home.model.IconModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import faranjit.currency.edittext.CurrencyEditText;

/**
 * Created by kris on 8/20/17. Tokopedia
 */

public class EditPaymentDialog extends NoTitleDialogFragment implements IconSelectetionListener{

    private static final String FINANCIAL_MODEL_KEY = "FINANCIAL_MODEL_KEY";

    private RecyclerView.Adapter iconListAdapter;
    private List<IconModel> generatedIcons;
    private EditItemListener listener;
    private EditText fieldInfo;
    private CurrencyEditText fieldAmount;
    private int iconId = 0;

    public static EditPaymentDialog createEditPaymentDialog(FinancialHistoryModel model) {
        EditPaymentDialog dialog = new EditPaymentDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(FINANCIAL_MODEL_KEY, model);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (EditItemListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (EditItemListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.edit_data_dialog, container);
        FinancialHistoryModel modelToBeEdited = getArguments().getParcelable(FINANCIAL_MODEL_KEY);
        fieldAmount = (CurrencyEditText) view
                .findViewById(R.id.edit_text_insert_amount);
        fieldInfo = (EditText) view.findViewById(R.id.edit_text_insert_info);
        TextView cancelButton = (TextView) view.findViewById(R.id.cancel_button);
        TextView finishButton = (TextView) view.findViewById(R.id.finish_button);
        RecyclerView iconList = (RecyclerView) view.findViewById(R.id.icon_list);
        fieldAmount.setText(String.valueOf(modelToBeEdited.getAmountUnformatted()));
        fieldInfo.setText(modelToBeEdited.getInfo());
        generatedIcons = generatedIconList();
        generatedIcons.get(modelToBeEdited.getTheme()).setChoosen(true);
        iconId = generatedIcons.get(modelToBeEdited.getTheme()).getIconImageId();
        iconListAdapter = new IconAdapter(generatedIcons, this);
        iconList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        iconList.setAdapter(iconListAdapter);
        finishButton.setOnClickListener(onFinishButtonClickedListener(modelToBeEdited));
        cancelButton.setOnClickListener(onCancelButtonClickedListener());
        return view;
    }

    @Override
    public void onIconClicked(int position, int iconId) {
        refreshIcons();
        generatedIcons.get(position).setChoosen(true);
        this.iconId = iconId;
        iconListAdapter.notifyDataSetChanged();
    }

    private void refreshIcons() {
        for (int i = 0; i < generatedIcons.size(); i++) {
            generatedIcons.get(i).setChoosen(false);
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

    private View.OnClickListener onCancelButtonClickedListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };
    }

    private View.OnClickListener onFinishButtonClickedListener(final FinancialHistoryModel model) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = fieldInfo.getText().toString();
                List<String> hashTagList = new ArrayList<>();
                populateHasTagList(info, hashTagList);
                String price = fieldAmount.getText().toString();
                model.setInfo(info);
                model.setAmount(price);
                model.setHashtag(hashTagList);
                model.setTheme(iconId);
                try {
                    model.setAmountUnformatted(fieldAmount.getCurrencyDouble());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                listener.onItemEdited();
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

    public interface EditItemListener{
        void onItemEdited();
    }
}
