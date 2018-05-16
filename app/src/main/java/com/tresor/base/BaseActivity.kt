package com.tresor.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.view.View

/**
 * @author by alvinatin on 21/04/18.
 */

abstract class BaseActivity<P : BasePresenter<BaseView>> : BaseView, AppCompatActivity() {
    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = instantiatePresenter()
    }

    protected abstract fun instantiatePresenter(): P

    fun <T : View> Activity.bind(@IdRes res : Int) : Lazy<T> {
        @Suppress("UNCHECKED_CAST")
        return lazy(LazyThreadSafetyMode.NONE){ findViewById(res) as T }
    }

    override fun getContext(): Context {
        return this
    }
}