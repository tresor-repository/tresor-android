package com.tresor.home.network

import android.content.Context
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.common.network.retrofit.TresorAuthenticatedService
import io.reactivex.Observable

/**
 * Created by kris on 12/30/17. Tokopedia
 */
class SpendingService : TresorAuthenticatedService() {

    val BASE_URL = "www.tresor.staging"

    fun getSpending(requestParameters: Map<String, String>,
                    context : Context) : Observable<List<SpendingModel>> {
        return buildRetrofit(BASE_URL, context)
                .create(SpendingApi::class.java).getListOfSpending(requestParameters)
    }

}