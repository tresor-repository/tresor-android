package com.tresor.home.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.tresor.R;
import com.tresor.common.activity.addpayment.PaymentTemplateInterface;
import com.tresor.common.adapter.AutoCompleteSuggestionAdapter;
import com.tresor.common.adapter.FilterAdapter;
import com.tresor.common.model.viewmodel.SpendingModel;
import com.tresor.common.widget.implementable.FilterAutoCompleteTextView;
import com.tresor.common.widget.template.SmartAutoCompleteTextView;
import com.tresor.home.activity.AddPaymentActivity;
import com.tresor.home.activity.EditPaymentActivity;
import com.tresor.home.adapter.TodayPageAdapter;
import com.tresor.home.adapter.TodayPageAdapterKotlin;
import com.tresor.home.inteface.HomeActivityListener;
import com.tresor.home.model.FinancialHistoryModel;
import com.tresor.home.model.SpendingModelWrapper;
import com.tresor.home.viewholder.TodayPageAdapterViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

import static com.tresor.home.inteface.HomeActivityListener.ADD_NEW_PAYMENT_REQUEST_CODE;
import static com.tresor.home.inteface.HomeActivityListener.EXTRA_ADD_DATA_RESULT;

/**
 * Created by kris on 5/27/17. Tokopedia
 */

public class ListFinancialHistoryFragment extends Fragment
        implements TodayPageAdapter.TodayAdapterListener,
        TodayPageAdapterViewHolder.TodaySpendingAdapterListener,
        FilterAdapter.onFilterItemClicked {

    private TodayPageAdapterKotlin financialHistoryListAdapter;
    private List<SpendingModel> financialList;

    public static ListFinancialHistoryFragment createFragment() {
        return new ListFinancialHistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(
                R.layout.fragment_list_financial_history,
                container, false
        );
        setAutoCompleteView(mainView);
        setSpendingList(mainView);
        return mainView;
    }

    private void setSpendingList(View mainView) {
        RecyclerView financialHistoryList = (RecyclerView) mainView
                .findViewById(R.id.list_financial_history);
        setSpendingAdapter(financialHistoryList);
        financialHistoryList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setSpendingAdapter(RecyclerView financialHistoryList) {
        financialList = spendingModelList();
        financialHistoryListAdapter = new TodayPageAdapterKotlin(financialList, this);
        financialHistoryList.setAdapter(financialHistoryListAdapter);
    }

    private void openAddTodaySpending() {
        Intent intent = new Intent(getActivity(), AddPaymentActivity.class);
        startActivityForResult(intent, ADD_NEW_PAYMENT_REQUEST_CODE);
    }

    private void setAutoCompleteView(View mainView) {
        FilterAdapter filterAdapter = new FilterAdapter(this);
        setFilterResultView(mainView, filterAdapter);
        setAutoSuggestionEditText(mainView, filterAdapter);
    }

    private void setFilterResultView(View mainView, FilterAdapter filterAdapter) {
        RecyclerView filterRecyclerView = (RecyclerView) mainView
                .findViewById(R.id.filter_recycler_view);
        filterRecyclerView.setAdapter(filterAdapter);
        filterRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        );
    }

    private void setAutoSuggestionEditText(View mainView, FilterAdapter filterAdapter) {
        FilterAutoCompleteTextView autoCompleteField = (FilterAutoCompleteTextView) mainView
                .findViewById(R.id.auto_complete_filter);
        AutoCompleteSuggestionAdapter arrayAdapter = new AutoCompleteSuggestionAdapter
                (getActivity());
        autoCompleteField.setAdapter(arrayAdapter);
        setDropDownResultAction(filterAdapter, autoCompleteField, arrayAdapter);
    }

    private void setDropDownResultAction(FilterAdapter filterAdapter,
                                         FilterAutoCompleteTextView autoCompleteField,
                                         AutoCompleteSuggestionAdapter arrayAdapter) {
        List<String> hashTagSuggestions = new ArrayList<>();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        autoCompleteField.initComponent(compositeDisposable, autoCompleteListener(arrayAdapter,
                hashTagSuggestions, autoCompleteField));
        autoCompleteField.setOnItemClickListener(onItemClickListener(hashTagSuggestions,
                filterAdapter, autoCompleteField));
    }

    private List<SpendingModel> selectedFilterResult(List<String> filteredTagList) {
        List<SpendingModel> filteredList = new ArrayList<>();
        for (int i = 0; i<financialList.size(); i++) {
            if(selectFilter(financialList.get(i).getHashTagString(), filteredTagList)) {
                filteredList.add(financialList.get(i));
            }
        }
        return filteredList;
    }

    private boolean selectFilter(String hashTagString, List<String> listOfFilters) {
        for (int i = 0; i < listOfFilters.size(); i++) {
            if(!hashTagString.toLowerCase().contains(listOfFilters.get(i).toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    private List<SpendingModel> spendingModelList() {
        List<SpendingModel> list = new ArrayList<>();
        for(int i = 0; i<8; i++) {
            List<String> hashTagList = new ArrayList<>();
            hashTagList.add("#Makan");
            hashTagList.add("#Siang");
            hashTagList.add("#Liburan");
            SpendingModel spendingModel = new SpendingModel(
                    i,
                    "Rp 50.000",
                    50000,
                    false,
                    1,
                    "#Makan#Siang#Liburan",
                    "08.32 WIB February 17th 2017",
                    i,
                    hashTagList,
                    "#Liburan #Makan Martabak Telor Mang Udin the Conqueror #Siang siang 3 Paket"
                    );
            list.add(spendingModel);
        }
        return list;
    }

    private void onDataAdded(SpendingModel newData) {
        financialList.add(0, newData);
        /*financialHistoryListAdapter
                .notifyItemInserted(FinancialHistoryAdapter.NUMBER_OF_HEADER_ADAPTER);
        financialHistoryListAdapter
                .notifyItemRangeInserted(
                        FinancialHistoryAdapter.NUMBER_OF_HEADER_ADAPTER,
                        financialList.size() + FinancialHistoryAdapter.NUMBER_OF_HEADER_ADAPTER
                );
        financialHistoryList.scrollToPosition(FinancialHistoryAdapter.NUMBER_OF_HEADER_ADAPTER);*/

        //TODO RELEASE IF ANIMATION CAUSES MUCH BUGS
        financialHistoryListAdapter.notifyDataSetChanged();
    }

    private void onDataEdited(SpendingModelWrapper alteredData) {
        financialList.set(alteredData.getPosition(), alteredData.getSpendingModel());
    }



    @Override
    public void onClick(FinancialHistoryModel itemModel) {
        Intent intent = new Intent(getActivity(), EditPaymentActivity.class);
        intent.putExtra(PaymentTemplateInterface.EXTRAS_OPEN_EDIT_PAYMENT_PAGE, itemModel);
        startActivityForResult(intent, HomeActivityListener.EDIT_PAYMENT_REQUEST_CODE);
    }

    public void onItemEdited() {
        financialHistoryListAdapter.notifyDataSetChanged();
    }

    private SmartAutoCompleteTextView
            .AutoCompleteListener autoCompleteListener(
                    final AutoCompleteSuggestionAdapter arrayAdapter,
                    final List<String> listOfHashTag,
                    final FilterAutoCompleteTextView autoCompleteTextView) {

        return new SmartAutoCompleteTextView.AutoCompleteListener() {
            @Override
            public void finishedTyping(String query) {
                listOfHashTag.clear();
                listOfHashTag.add("makan");
                listOfHashTag.add("siang");
                listOfHashTag.add("liburan");
                listOfHashTag.add("pup");
                arrayAdapter.updateData(listOfHashTag);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTypingError(Throwable e) {

            }

            @Override
            public void onEditTextEmptied() {

            }

            @Override
            public void onEnterKeyPressed() {
                updateAdapter(autoCompleteTextView);
            }
        };
    }

    private AdapterView.OnItemClickListener onItemClickListener(
            final List<String> autoCompleteHashTagList,
            final FilterAdapter filterAdapter,
            final FilterAutoCompleteTextView autoCompleteTextView
    ) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                filterAdapter.addNewHashTag(autoCompleteHashTagList.get(position));
                financialHistoryListAdapter
                        .updateData(selectedFilterResult(filterAdapter.getHashTagShownInAdapter()));
                financialHistoryListAdapter.notifyDataSetChanged();
                autoCompleteTextView.setText("");
                autoCompleteTextView.requestFocus();
                /*autoCompleteTextView.addNewString(autoCompleteHashTagList.get(position));
                updateAdapter(autoCompleteTextView);
                autoCompleteTextView.setSelection(autoCompleteTextView.getText().length());*/
            }
        };
    }

    private void updateAdapter(FilterAutoCompleteTextView autoCompleteTextView) {
        financialHistoryListAdapter
                .updateData(selectedFilterResult(autoCompleteTextView.getSeparatedString()));
        financialHistoryListAdapter.notifyDataSetChanged();
        autoCompleteTextView.requestFocus();
    }

    @Override
    public void onFilterItemRemoved(List<String> hashTagList) {
        if (hashTagList.size() == 0)  financialHistoryListAdapter.updateData(financialList);
        else financialHistoryListAdapter.updateData(selectedFilterResult(hashTagList));
        financialHistoryListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NEW_PAYMENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            onDataAdded(((SpendingModelWrapper) data.getParcelableExtra(EXTRA_ADD_DATA_RESULT))
                    .getSpendingModel());
        } else {
            onDataEdited((SpendingModelWrapper) data.getParcelableExtra(EXTRA_ADD_DATA_RESULT));
        }
    }

    @Override
    public void onHeaderClickedListener() {
        openAddTodaySpending();
    }

    @Override
    public void onItemClicked(int position, @NotNull SpendingModel spendingModel) {
        Intent intent = new Intent(getActivity(), EditPaymentActivity.class);
        SpendingModelWrapper spendingModelWrapper = new SpendingModelWrapper(
                position, spendingModel
        );
        intent.putExtra(PaymentTemplateInterface.EXTRAS_OPEN_EDIT_PAYMENT_PAGE, spendingModelWrapper);
        startActivityForResult(intent, HomeActivityListener.EDIT_PAYMENT_REQUEST_CODE);
    }

    @Override
    public void onHeaderClicked() {
        openAddTodaySpending();
    }
}
