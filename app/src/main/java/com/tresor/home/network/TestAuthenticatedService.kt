package com.tresor.home.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.tresor.common.exception.NoCredentialException
import com.tresor.common.model.testmodel.TestModel

import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by kris on 12/30/17. Tokopedia
 */

class TestAuthenticatedService {

    public fun getTestList(): Observable<List<TestModel>> {
        return buildRetrofit("https://learn2crack-json.herokuapp.com").create(TestApi::class.java).getTestData()
    }

    private fun buildRetrofit(endPoint: String): Retrofit {
        return Retrofit.Builder().baseUrl(endPoint)
                .client(modifiedHttpClient(buildInterceptor()))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun modifiedHttpClient(serviceInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(serviceInterceptor).build()
    }

    private fun buildInterceptor(): Interceptor {
        return Interceptor { chain ->  chain.proceed(buildHeader(chain))}
    }

    private fun buildHeader(chain : Interceptor.Chain) : Request {
        if(getUserToken().isEmpty()) {
            throw NoCredentialException("Session Expired")
        } else return chain.request().newBuilder()
                .header("TOKEN_ID", getUserToken())
                .method(chain.request().method(), chain.request().body())
                .build()
    }

    private fun getUserToken() : String {
        return "TEST_HEADER"
    }

}
