package com.tresor.session.register.data.source

import com.tresor.R.string.name
import com.tresor.R.string.password
import com.tresor.session.common.api.SessionApi
import com.tresor.session.common.api.SessionRequestBody
import com.tresor.session.register.data.RegisterMapper
import com.tresor.session.register.data.pojo.RegisterResponse
import com.tresor.session.register.view.RegisterViewModel
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

/**
 * @author by alvinatin on 21/04/18.
 */
class RegisterDataSource (private val sessionApi: SessionApi,
                                             private val registerMapper: RegisterMapper) {

//    fun callRegister(email: String, password: String): Flowable<RegisterViewModel>{
//        return sessionApi.register(getRequestBody(email, password)).map(registerMapper)
//    }
//
//    fun getRequestBody(email: String, password: String) : SessionRequestBody {
//        return SessionRequestBody(email, password)
//    }
}