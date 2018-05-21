package com.tresor.home.network

import com.tresor.common.model.viewmodel.SpendingModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap


/**
 * Created by kris on 12/30/17. Tokopedia
 */

interface SpendingApi {

    @GET("api/android")
    fun getListOfSpending(@QueryMap requestParameters : Map<String, String>)
            : Observable<List<SpendingModel>>

}