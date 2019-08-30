package com.enesgemci.loginvuz.ui.login

import com.enesgemci.loginvuz.core.base.BasePresenter
import com.enesgemci.loginvuz.core.util.ValidationUtil
import com.enesgemci.loginvuz.data.network.LoginInteractor
import javax.inject.Inject

class LoginPresenter @Inject constructor(private val loginInteractor: LoginInteractor) :
    BasePresenter<LoginView>() {

    fun login(email: String, password: String) {
        if (validateLoginData(email, password)) {

            ifViewAttached {
                it.loginOnProgress()
            }

            loginInteractor.login(email, password).call(
                onSuccess = {
                    ifViewAttached {
                        it.dismissLoadingDialog()
                        it.onLoginSuccess()
                    }
                },
                onError = {
                    ifViewAttached {
                        it.dismissLoadingDialog()
                        it.onLoginFailed()
                    }
                }
            )
        }
    }

    private fun validateLoginData(email: String, password: String): Boolean {

        var ret = true

        if (!isEmailValid(email)) {
            view?.onEmailValidationError()
            ret = false
        }
        if (!isPasswordValid(password)) {
            view?.onPasswordValidationError()
            ret = false
        }

        return ret
    }

    private fun isEmailValid(email: String): Boolean {
        return ValidationUtil.isEmailValid(email)
    }

    private fun isPasswordValid(password: String): Boolean {
        return ValidationUtil.isPasswordValid(password)
    }
}