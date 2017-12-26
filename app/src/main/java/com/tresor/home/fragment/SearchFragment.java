package com.tresor.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import com.tresor.R;
import com.tresor.common.adapter.AutoCompleteSuggestionAdapter;
import com.tresor.common.adapter.FilterAdapter;
import com.tresor.common.fragment.DateRangeFragment;
import com.tresor.common.widget.implementable.FilterAutoCompleteTextView;
import com.tresor.common.widget.template.SmartAutoCompleteTextView;
import com.tresor.home.adapter.FinancialHistoryAdapter;
import com.tresor.home.model.FinancialHistoryModel;
import com.tresor.home.model.SpendingDataModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by kris on 10/18/17. Tokopedia
 */

public class SearchFragment extends DateRangeFragment
        implements FinancialHistoryAdapter.ListItemListener, FilterAdapter.onFilterItemClicked{

    private FinancialHistoryAdapter financialHistoryAdapter;
    private List<FinancialHistoryModel> financialList;

    public static SearchFragment createInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initMainView(View mainView) {
        FilterAutoCompleteTextView filterField = (FilterAutoCompleteTextView) mainView
                .findViewById(R.id.auto_complete_search_filter);
        RecyclerView recyclerView = (RecyclerView) mainView
                .findViewById(R.id.search_recycler_view);
        RecyclerView filterRecyclerView = (RecyclerView) mainView
                .findViewById(R.id.filter_recycler_view);
        FilterAdapter filterAdapter = new FilterAdapter(this);
        filterRecyclerView.setAdapter(filterAdapter);
        AutoCompleteSuggestionAdapter arrayAdapter = new AutoCompleteSuggestionAdapter
                (getActivity());
        filterField.setAdapter(arrayAdapter);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        List<String> hashTagSuggestions = new ArrayList<>();
        filterField.initComponent(compositeDisposable, autoCompleteListener(arrayAdapter,
                hashTagSuggestions, filterField));
        filterField.setOnItemClickListener(onItemClickListener(hashTagSuggestions,
                filterAdapter, filterField));
        financialList = financialHistoryModelList();
        financialHistoryAdapter = new FinancialHistoryAdapter(
                getActivity(),
                spendingDataModel(),
                this);
        recyclerView.setAdapter(financialHistoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_hashtag;
    }

    @Override
    protected EditText startDateEditText(View view) {
        return (EditText) view.findViewById(R.id.start_date_field);
    }

    @Override
    protected EditText endDateEditText(View view) {
        return (EditText) view.findViewById(R.id.end_date_field);
    }

    @Override
    protected void startDateChanged(int date, int month, int year) {

    }

    @Override
    protected void endDateChanged(int date, int month, int year) {

    }

    private SpendingDataModel spendingDataModel() {
        SpendingDataModel model = new SpendingDataModel();
        model.setDailyAllocation(0);
        model.setDailyAllocationString("Rp 0");
        model.setFinancialHistoryModelList(financialHistoryModelList());
        model.setHistory(false);
        model.setTodayAllocation(0);
        model.setTodayAllocationString("Rp 0");
        model.setTodaySaving(0);
        model.setTodaySavingString("Rp 0");
        model.setTotalSpending(250000);
        model.setTotalSpendingString("Rp 250.000");
        return model;
    }

    private List<FinancialHistoryModel> financialHistoryModelList() {
        List<FinancialHistoryModel> list = new ArrayList<>();
        for(int i = 0; i<8; i++) {
            FinancialHistoryModel financialHistoryModel = new FinancialHistoryModel();
            financialHistoryModel.setAmountUnformatted(50000);
            financialHistoryModel.setAmount("Rp 50.000");
            financialHistoryModel.setDate("08.32 WIB February 17th 2017");
            List<String> hashTagList = new ArrayList<>();
            hashTagList.add("#Makan");
            hashTagList.add("#Siang");
            hashTagList.add("#Liburan");
            financialHistoryModel.setHashtag(hashTagList);
            financialHistoryModel
                    .setInfo("#Liburan #Makan Martabak Telor Mang Udin the Conqueror #Siang siang 3 Paket");
            if(i > 4) {
                financialHistoryModel.setTheme(i - 5);
            } else financialHistoryModel.setTheme(i);
            list.add(financialHistoryModel);
        }
        return list;
    }

    @Override
    public void onClick(FinancialHistoryModel itemModel) {

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
                /*financialHistoryListAdapter.updateData(financialList);
                financialHistoryListAdapter.notifyDataSetChanged();*/
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
                financialHistoryAdapter
                        .updateData(selectedFilterResult(filterAdapter.getHashTagShownInAdapter()));
                financialHistoryAdapter.notifyDataSetChanged();
                autoCompleteTextView.setText("");
                autoCompleteTextView.requestFocus();
            }
        };
    }

    @Override
    public void onFilterItemRemoved(List<String> hashTagList) {

    }

    private List<FinancialHistoryModel> selectedFilterResult(List<String> filteredTagList) {
        List<FinancialHistoryModel> filteredList = new ArrayList<>();
        for (int i = 0; i<financialList.size(); i++) {
            if(selectFilter(financialList.get(i).getHashTagString(), filteredTagList)) {
                filteredList.add(financialList.get(i));
            }
        }
        return filteredList;
    }

    private void updateAdapter(FilterAutoCompleteTextView autoCompleteTextView) {
        financialHistoryAdapter
                .updateData(selectedFilterResult(autoCompleteTextView.getSeparatedString()));
        financialHistoryAdapter.notifyDataSetChanged();
        autoCompleteTextView.requestFocus();
    }

    private boolean selectFilter(String hashTagString, List<String> listOfFilters) {
        for (int i = 0; i < listOfFilters.size(); i++) {
            if(!hashTagString.toLowerCase().contains(listOfFilters.get(i).toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}
