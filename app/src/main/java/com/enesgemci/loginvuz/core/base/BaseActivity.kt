package com.enesgemci.loginvuz.core.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.enesgemci.loginvuz.core.extension.isConnectedToNet
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity<V : BaseView, P : BasePresenter<V>> : MvpActivity<V, P>(), BaseView,
    HasSupportFragmentInjector {

    override val isConnected: Boolean
        get() = isConnectedToNet

    @Inject
    internal lateinit var injectedPresenterDontUseThisDirectly: P

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun createPresenter(): P {
        return injectedPresenterDontUseThisDirectly
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun showLoadingDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoadingDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}