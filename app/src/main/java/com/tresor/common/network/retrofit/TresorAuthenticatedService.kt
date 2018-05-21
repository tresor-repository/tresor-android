package com.tresor.common.network.retrofit

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.tresor.common.exception.NoCredentialException
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by kris on 11/14/17. Tokopedia
 */

abstract class TresorAuthenticatedService {

    protected fun buildRetrofit(endPoint: String, context: Context): Retrofit {
        return Retrofit.Builder().baseUrl(endPoint)
                .client(modifiedHttpClient(buildInterceptor(context)))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun modifiedHttpClient(serviceInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(serviceInterceptor).build()
    }

    private fun buildInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->  chain.proceed(buildHeader(context, chain))}
    }

    private fun buildHeader(context: Context, chain : Interceptor.Chain) : Request {
        if(getUserToken(context).isEmpty()) {
          throw NoCredentialException("Session Expired")
        } else return chain.request().newBuilder()
                .header("TOKEN_ID", getUserToken(context))
                .method(chain.request().method(), chain.request().body())
                .build()
    }

    private fun getUserToken(context: Context) : String {
        return context
                .getSharedPreferences("USER_CREDENTIAL", Context.MODE_PRIVATE)
                .getString("USER_TOKEN", "")
    }

}
