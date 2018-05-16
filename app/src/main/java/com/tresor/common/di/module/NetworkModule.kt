package com.tresor.common.di.module

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.tresor.base.BaseUrl
import com.tresor.common.di.qualifier.ApplicationContext
import com.tresor.common.di.scope.ApplicationScope
import com.tresor.common.network.OkHttpRetryPolicy
import com.tresor.common.utils.NET_CONNECT_TIMEOUT
import com.tresor.common.utils.NET_READ_TIMEOUT
import com.tresor.common.utils.NET_RETRY
import com.tresor.common.utils.NET_WRITE_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author by alvinatin on 10/03/18.
 */
@Module
@Suppress("unused")
object NetworkModule {

    @Provides
    @JvmStatic
    internal fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @JvmStatic
    internal fun provideOkHttpClient(okHttpRetryPolicy: OkHttpRetryPolicy,
                                     @ApplicationScope okHttpLoggingInterceptor:
                                     HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(okHttpRetryPolicy.connectTimeout, TimeUnit.SECONDS)
                .readTimeout(okHttpRetryPolicy.readTimeout, TimeUnit.SECONDS)
                .writeTimeout(okHttpRetryPolicy.readTimeout, TimeUnit.SECONDS)
                .addInterceptor(okHttpLoggingInterceptor)
                .build()
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofit(retrofitBuilder: Retrofit.Builder,
                                 okHttpClient: OkHttpClient): Retrofit {
        return retrofitBuilder
                .baseUrl(BaseUrl.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @JvmStatic
    internal fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
    }


    @Provides
    @JvmStatic
    internal fun provideOkHttpRetryPolicy(): OkHttpRetryPolicy {
        return OkHttpRetryPolicy(NET_READ_TIMEOUT,
                NET_WRITE_TIMEOUT,
                NET_CONNECT_TIMEOUT,
                NET_RETRY)
    }

    @ApplicationScope
    @Provides
    @JvmStatic
    internal fun provideHttpLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}