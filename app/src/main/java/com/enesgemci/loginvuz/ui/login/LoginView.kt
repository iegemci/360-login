package com.enesgemci.loginvuz.ui.login

import com.enesgemci.loginvuz.core.base.BaseView

interface LoginView : BaseView {

    fun onLoginFailed()

    fun onEmailValidationError()

    fun onPasswordValidationError()

    fun onLoginSuccess()

    fun loginOnProgress()
}