package com.tresor.session.common.di

import com.tresor.common.di.qualifier.ApplicationContext
import com.tresor.session.common.api.SessionApi
import com.tresor.session.register.view.listener.RegisterView
import com.tresor.session.register.view.presenter.RegisterPresenter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * @author by alvinatin on 07/03/18.
 */
@Module
class SessionModule {

    @SessionScope
    @Provides
    fun provideSessioApi(retrofit: Retrofit) : SessionApi{
        return retrofit.create(SessionApi::class.java)
    }
}