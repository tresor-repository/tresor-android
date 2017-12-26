package com.tresor.common.widget.implementable;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.tresor.common.widget.template.SmartAutoCompleteTextView;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by kris on 9/22/17. Tokopedia
 */

public class DebouncingAutoCompleteTextView extends SmartAutoCompleteTextView{

    public DebouncingAutoCompleteTextView(Context context) {
        super(context);
    }

    public DebouncingAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DebouncingAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected TextWatcher getTextWatcher() {
        return textWatcher();
    }

    public void initListener(CompositeDisposable compositeDisposable,
                             AutoCompleteListener listener) {
        initAttribute(compositeDisposable, listener);
    }

    private TextWatcher textWatcher() {
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
                    textListener.onQueryTextChanged(s.toString());
                }
            }
        };
    }

}