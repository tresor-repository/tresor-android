package com.tresor.common.widget.implementable;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.tresor.common.widget.template.SmartAutoCompleteTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by kris on 9/29/17. Tokopedia
 */

public class FilterAutoCompleteTextView extends SmartAutoCompleteTextView{

    private Context context;

    private StringBuilder accumulatedString;

    public FilterAutoCompleteTextView(Context context) {
        super(context);
        this.context = context;
        initStringBuilder();
    }

    public FilterAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initStringBuilder();
    }

    public FilterAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initStringBuilder();
    }

    private void initStringBuilder() {
        accumulatedString = new StringBuilder("");
    }

    public void initComponent(CompositeDisposable compositeDisposable,
                              AutoCompleteListener listener) {
        initAttribute(compositeDisposable, listener);
    }

    @Override
    protected TextWatcher getTextWatcher() {
        return setTextWatcher();
    }

    public void addNewString(String newKeyWord) {
        newKeyWord += " ";
        accumulatedString.append(newKeyWord);
        setText(Html.fromHtml("<b>" + accumulatedString + "</b>"));
    }

    public List<String> getSeparatedString() {
        return new ArrayList<>(Arrays.asList(accumulatedString.toString().split(" ")));
    }

    protected TextWatcher setTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(textListener != null) {
                    String filteredString = s.toString().replace(accumulatedString, "");
                    textListener.onQueryTextChanged(filteredString);
                }
            }
        };
    }

}
