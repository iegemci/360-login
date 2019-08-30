package com.enesgemci.loginvuz.core.base

import com.hannesdorfmann.mosby3.mvp.MvpView

interface BaseView : MvpView {

    val isConnected: Boolean

    fun showLoadingDialog()

    fun dismissLoadingDialog()
}