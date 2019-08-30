package com.enesgemci.loginvuz.ui.dashboard

import androidx.lifecycle.LiveData
import com.enesgemci.loginvuz.core.base.BasePresenter
import com.enesgemci.loginvuz.data.network.LoginInteractor
import com.enesgemci.loginvuz.data.persistent.User
import javax.inject.Inject

class DashboardPresenter @Inject constructor(
    private val loginInteractor: LoginInteractor
) : BasePresenter<DashboardView>() {

    val userLiveData: LiveData<User> = loginInteractor.getUser()

    fun logout() {
        loginInteractor.logout().call(
            onSuccess = {
                ifViewAttached { view ->
                    view.dismissLoadingDialog()
                    view.logoutSuccess()
                }
            },
            onError = {
                ifViewAttached { view ->
                    view.dismissLoadingDialog()
                    view.logoutError()
                }
            }
        )
    }
}