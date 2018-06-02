package com.tresor.session.register.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.tresor.R
import com.tresor.TresorApp
import com.tresor.base.BaseActivity
import com.tresor.session.common.di.DaggerSessionComponent
import com.tresor.session.common.di.SessionComponent
import com.tresor.session.common.di.SessionModule
import com.tresor.session.register.view.RegisterViewModel
import com.tresor.session.register.view.listener.RegisterView
import com.tresor.session.register.view.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*

/**
 * @author by alvinatin on 07/03/18.
 */

class RegisterActivity : BaseActivity<RegisterPresenter>(), RegisterView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        button_register.setOnClickListener { v ->
            presenter.callRegister(et_name.text.toString(), et_password.text.toString())
        }

        initInjector().inject(presenter)
    }

    fun initInjector() : SessionComponent {
        return DaggerSessionComponent.builder()
                .baseAppComponent((application as TresorApp).getBaseAppComponent)
                .sessionModule(SessionModule())
                .build()
    }

    override fun instantiatePresenter(): RegisterPresenter {
        return RegisterPresenter(this)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onCallRegister(email: String, password: String) {
    }

    override fun onSuccesRegister(registerViewModel: RegisterViewModel) {
        //TODO ATIN add after register action
        Toast.makeText(this, "sukses broh", Toast.LENGTH_LONG).show()
    }

    override fun onErrorRegister(registerViewModel: RegisterViewModel) {
    }

    override fun showError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}