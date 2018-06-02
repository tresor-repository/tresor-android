package com.tresor.base

import com.tresor.common.di.component.BaseAppComponent
import com.tresor.common.di.module.AppModule
import com.tresor.common.di.module.NetworkModule

/**
 * @author by alvinatin on 21/04/18.
 */

abstract class BasePresenter<out V : BaseView>(protected val view: V) {

    open fun onViewCreated(){}

    open fun onViewDestroyed(){}

}