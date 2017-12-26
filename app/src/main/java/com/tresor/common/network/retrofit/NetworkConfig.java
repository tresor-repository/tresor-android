package com.tresor.common.network.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kris on 11/14/17. Tokopedia
 */

public abstract class NetworkConfig {

    protected Retrofit buildRetrofit(String endPoint) {
        return new Retrofit.Builder().baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }



}
