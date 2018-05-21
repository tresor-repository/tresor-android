package com.tresor.home.network

import com.tresor.common.model.testmodel.TestModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by kris on 12/30/17. Tokopedia
 */
interface TestApi {

    @GET("api/android")
    fun getTestData()
            : Observable<List<TestModel>>

}