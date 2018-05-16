package com.tresor.session.login.di

import com.tresor.session.common.di.SessionComponent
import dagger.Component

/**
 * @author by alvinatin on 10/03/18.
 */
@Component(
        modules = arrayOf(LoginModule::class),
        dependencies = arrayOf(SessionComponent::class)
)
@LoginScope
interface LoginComponent {

}