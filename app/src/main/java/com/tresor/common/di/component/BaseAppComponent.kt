package com.tresor.common.di.component

import android.content.Context
import com.google.gson.Gson
import com.tresor.common.di.module.AppModule
import com.tresor.common.di.module.NetworkModule
import com.tresor.common.di.qualifier.ApplicationContext
import com.tresor.common.di.scope.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * @author by alvinatin on 10/03/18.
 */
@ApplicationScope
@Component(modules = [(AppModule::class)])
interface BaseAppComponent {

    @Component.Builder
    interface Builder {

        fun build(): BaseAppComponent
        fun appModule(appModule: AppModule) : Builder
    }

    @ApplicationContext
    fun getContext(): Context

    fun getGson(): Gson

    fun exposeRetrofit() : Retrofit
}