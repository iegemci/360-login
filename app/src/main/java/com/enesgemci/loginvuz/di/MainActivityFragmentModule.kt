package com.enesgemci.loginvuz.di

import com.enesgemci.loginvuz.ui.dashboard.DashboardFragment
import com.enesgemci.loginvuz.ui.login.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): DashboardFragment
}