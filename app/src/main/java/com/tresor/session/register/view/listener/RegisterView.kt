package com.tresor.session.register.view.listener

import com.tresor.base.BaseView
import com.tresor.session.register.view.RegisterViewModel

/**
 * @author by alvinatin on 22/04/18.
 */

interface RegisterView : BaseView {

    fun showLoading()

    fun hideLoading()

    fun onCallRegister(email: String, password: String)

    fun onSuccesRegister(registerViewModel: RegisterViewModel)

    fun onErrorRegister(registerViewModel: RegisterViewModel)

    fun showError(error: String?)
}
