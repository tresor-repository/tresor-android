package com.tresor.session.register.data

import com.tresor.session.register.data.pojo.RegisterResponse
import com.tresor.session.register.view.RegisterViewModel
import io.reactivex.functions.Function
import retrofit2.Response
import javax.inject.Inject

/**
 * @author by alvinatin on 22/04/18.
 */

class RegisterMapper @Inject constructor() : Function<Response<RegisterResponse>, RegisterViewModel> {

    override fun apply(t: Response<RegisterResponse>): RegisterViewModel {
        return checkIfError(t)
    }

    fun checkIfError(registerResponse: Response<RegisterResponse>): RegisterViewModel {
        if (registerResponse.isSuccessful) {
            return RegisterViewModel(true)
        } else {
            if (registerResponse.body() == null)
                return RegisterViewModel(false)
            else {
                val body = registerResponse.body()
                val field: String = body!!.data.field
                val message: String = body.data.message
                return RegisterViewModel(false, RegisterViewModel.Fields(field, message))
            }
        }
    }
}