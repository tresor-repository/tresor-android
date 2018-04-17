package com.tresor.statistic.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.tresor.R;
import com.tresor.common.adapter.AutoCompleteSuggestionAdapter;
import com.tresor.common.widget.implementable.DebouncingAutoCompleteTextView;
import com.tresor.common.widget.template.SmartAutoCompleteTextView;
import com.tresor.statistic.adapter.AnalyzeHashTagAdapter;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by kris on 9/10/17. Tokopedia
 */

public class AnalyzeHashTagSpendingDialog extends DialogFragment {

    private static final String HASH_TAG_LIST_KEY = "hash_tag_list_key";

    private CompositeDisposable compositeDisposable;

    private AnalyzeHashTagDialogListener listener;

    public static AnalyzeHashTagSpendingDialog createDialog(ArrayList<String> hashTagList) {
        AnalyzeHashTagSpendingDialog dialog = new AnalyzeHashTagSpendingDialog();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(HASH_TAG_LIST_KEY, hashTagList);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (AnalyzeHashTagDialogListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (AnalyzeHashTagDialogListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.analyze_hashtag_dialog, container);
        compositeDisposable = new CompositeDisposable();

        List<String> autoCompleteList = new ArrayList<>();

        List<String> analyzedHashTagList = getArguments().getStringArrayList(HASH_TAG_LIST_KEY);

        initView(view, autoCompleteList, analyzedHashTagList);

        return view;
    }

    private void initView(View view, List<String> listOfHashTag, List<String> analyzedHashTagList) {
        RecyclerView hashTagList = (RecyclerView) view.findViewById(R.id.hashTagList);
        DebouncingAutoCompleteTextView autoCompleteTextView = (DebouncingAutoCompleteTextView)
                view.findViewById(R.id.autoCompleteTextView);
        TextView okayButton = (TextView) view.findViewById(R.id.okayButton);

        AnalyzeHashTagAdapter analyzeHashTagAdapter = new AnalyzeHashTagAdapter(analyzedHashTagList);
        AutoCompleteSuggestionAdapter arrayAdapter = new AutoCompleteSuggestionAdapter(getActivity());

        setupAtutoCompleteTextView(listOfHashTag, autoCompleteTextView, arrayAdapter);
        setupHashTagListProperties(hashTagList, analyzeHashTagAdapter);

        autoCompleteTextView.setOnItemClickListener(
                onItemClickListener(analyzeHashTagAdapter, listOfHashTag)
        );
        okayButton.setOnClickListener(onOkayButtonClickedListener(analyzedHashTagList));
    }

    private void setupHashTagListProperties(RecyclerView hashTagList, AnalyzeHashTagAdapter analyzeHashTagAdapter) {
        hashTagList.setAdapter(analyzeHashTagAdapter);
        hashTagList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupAtutoCompleteTextView(List<String> listOfHashTag, DebouncingAutoCompleteTextView autoCompleteTextView, AutoCompleteSuggestionAdapter arrayAdapter) {
        autoCompleteTextView.initListener(
                compositeDisposable,
                debouncingAutoCompleteListener(arrayAdapter, listOfHashTag)
        );

        autoCompleteTextView.setAdapter(arrayAdapter);
    }

    private SmartAutoCompleteTextView.AutoCompleteListener debouncingAutoCompleteListener(
            final AutoCompleteSuggestionAdapter arrayAdapter,
            final List<String> listOfHashTag) {
        return new SmartAutoCompleteTextView.AutoCompleteListener() {
            @Override
            public void finishedTyping(String query) {
                listOfHashTag.clear();
                listOfHashTag.add("#makan" + query);
                listOfHashTag.add("#gemuk" + query);
                listOfHashTag.add("#kawai" + query);
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

            }
        };
    }

    private AdapterView.OnItemClickListener onItemClickListener(
            final AnalyzeHashTagAdapter adapter,
            final List<String> autoCompleteHashTagList
    ) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.addNewItem(autoCompleteHashTagList.get(position));
                adapter.notifyDataSetChanged();
            }
        };
    }

    private View.OnClickListener onOkayButtonClickedListener(final List<String> listHashTag) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFinishChoosingSpendingDialog(listHashTag);
                dismiss();
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    public interface AnalyzeHashTagDialogListener {
        void onFinishChoosingSpendingDialog(List<String> hashTagList);
    }
}
