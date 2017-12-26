package com.tresor.common.widget.template;

import android.content.Context;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kris on 9/29/17. Tokopedia
 */

public abstract class SmartAutoCompleteTextView extends AppCompatAutoCompleteTextView {

    protected textChangedListener textListener;

    public SmartAutoCompleteTextView(Context context) {
        super(context);
    }

    public SmartAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void initAttribute(CompositeDisposable compositeDisposable,
                                 AutoCompleteListener listener) {
        modifiedCompositeDisposable(compositeDisposable, listener);
        addTextChangedListener(getTextWatcher());
        setOnKeyListener(onKeyListener(listener));
    }

    protected abstract TextWatcher getTextWatcher();

    private CompositeDisposable modifiedCompositeDisposable(
            CompositeDisposable compositeDisposable,
            AutoCompleteListener autoCompleteListener) {
        compositeDisposable.add(Observable.create(onSubscribeDebounce())
                .debounce(500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(debounceDisposableObserver(autoCompleteListener)));
        return compositeDisposable;
    }

    private ObservableOnSubscribe<String> onSubscribeDebounce() {
        return new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> subscriber) throws Exception {
                textListener = new textChangedListener() {
                    @Override
                    public void onQueryTextChanged(String query) {
                        subscriber.onNext(query);
                    }
                };
            }
        };
    }

    private DisposableObserver<String> debounceDisposableObserver(final AutoCompleteListener
                                                                          listener) {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {
                if(s.isEmpty()) listener.onEditTextEmptied();
                else listener.finishedTyping(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private OnKeyListener onKeyListener(final AutoCompleteListener
                                                listener) {
        return new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            listener.onEnterKeyPressed();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        };
    }

    protected interface textChangedListener {
        void onQueryTextChanged(String query);
    }


    public interface AutoCompleteListener {
        void finishedTyping(String query);

        void onTypingError(Throwable e);

        void onEditTextEmptied();

        void onEnterKeyPressed();
    }

}
