package com.tresor.common.di.module

import android.app.Application
import android.content.Context
import com.tresor.base.BaseView
import com.tresor.common.di.qualifier.ApplicationContext
import com.tresor.common.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

/**
 * @author by alvinatin on 10/03/18.
 */

@Module(includes = [(NetworkModule::class)])
class AppModule(private val context: Context) {

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return context
    }

//    @Provides
//    @ApplicationScope
//    fun provideApplication(context: Context) : Application {
//        return context.applicationContext as Application
//    }
}