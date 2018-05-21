package com.tresor.common.network.retrofit

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by kris on 1/1/18. Tokopedia
 */

abstract class TresorGuestService {

    protected fun buildRetrofit(endPoint: String): Retrofit {
        return Retrofit.Builder().baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

}
