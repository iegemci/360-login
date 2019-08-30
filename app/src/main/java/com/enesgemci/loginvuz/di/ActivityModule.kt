package com.enesgemci.loginvuz.di

import com.enesgemci.loginvuz.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [MainActivityFragmentModule::class])
    internal abstract fun contributeMainActivity(): MainActivity
}
