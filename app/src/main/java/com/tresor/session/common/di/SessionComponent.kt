package com.tresor.session.common.di

import com.tresor.common.di.component.BaseAppComponent
import com.tresor.common.di.qualifier.ApplicationContext
import com.tresor.session.register.view.activity.RegisterActivity
import com.tresor.session.register.view.presenter.RegisterPresenter
import dagger.Component
import retrofit2.Retrofit

/**
 * @author by alvinatin on 10/03/18.
 */

@SessionScope
@Component(
        modules = arrayOf(SessionModule::class),
        dependencies = arrayOf(BaseAppComponent::class)
)
interface SessionComponent {

    fun inject(registerPresenter: RegisterPresenter)

    @Component.Builder
    interface Builder {
        fun build() : SessionComponent
        fun baseAppComponent(baseAppComponent: BaseAppComponent) : Builder
        fun sessionModule(sessionModule: SessionModule): Builder
    }
}
