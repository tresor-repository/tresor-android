package com.tresor

import android.app.Application
import com.tresor.common.di.component.BaseAppComponent
import com.tresor.common.di.component.DaggerBaseAppComponent
import com.tresor.common.di.module.AppModule
import com.tresor.common.di.module.NetworkModule

/**
 * @author by alvinatin on 29/04/18.
 */

class TresorApp : Application() {

    val getBaseAppComponent: BaseAppComponent by lazy {
        DaggerBaseAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}