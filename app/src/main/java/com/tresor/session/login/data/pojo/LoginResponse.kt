package com.tresor.session.login.data.pojo

/**
 * @author by alvinatin on 10/03/18.
 */

data class LoginResponse(val data: LoginDetailResponse)

data class LoginDetailResponse(val Field: String,
                               val Message: String)