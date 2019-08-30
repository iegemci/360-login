package com.enesgemci.loginvuz.di

import android.app.Application
import com.enesgemci.loginvuz.core.base.MApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityModule::class,
        NetworkModule::class,
        PersistenceModule::class
    ]
)
interface AppComponent : AndroidInjector<MApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: MApp)
}
