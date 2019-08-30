package com.enesgemci.loginvuz.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.enesgemci.loginvuz.core.extension.isConnectedToNet
import com.enesgemci.loginvuz.core.view.MProgressDialog
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlin.properties.Delegates

abstract class BaseFragment<V : BaseView, P : BasePresenter<V>>(@LayoutRes private val resId: Int) :
    MvpFragment<V, P>(), BaseView {

    private var progressDialog: MProgressDialog by Delegates.notNull()

    @Inject
    internal lateinit var injectedPresenterDontUseThisDirectly: P

    override val isConnected: Boolean
        get() = requireContext().isConnectedToNet

    abstract fun onFragmentCreated()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = MProgressDialog(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(resId, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentCreated()
    }

    override fun createPresenter(): P {
        return injectedPresenterDontUseThisDirectly
    }

    override fun showLoadingDialog() {
        progressDialog.show()
    }

    override fun dismissLoadingDialog() {
        progressDialog.dismiss()
    }
}