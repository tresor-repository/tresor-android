package com.tresor.session.common.api

import com.tresor.session.login.data.pojo.LoginResponse
import com.tresor.session.register.data.pojo.RegisterResponse
import io.reactivex.Flowable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

/**
 * @author by alvinatin on 10/03/18.
 */
interface SessionApi {
    @FormUrlEncoded
    @GET("/user")
    @Headers("Content-Type: application/json")
    fun login(@Body requestBody: SessionRequestBody): Flowable<LoginResponse>

    @FormUrlEncoded
    @POST("/user")
    @Headers("Content-Type: application/json")
    fun register(@Body requestBody: SessionRequestBody): Flowable<Response<RegisterResponse>>
}