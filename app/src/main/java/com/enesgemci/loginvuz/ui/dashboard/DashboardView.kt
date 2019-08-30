package com.enesgemci.loginvuz.ui.dashboard

import com.enesgemci.loginvuz.core.base.BaseView

interface DashboardView : BaseView {

    fun logoutSuccess()

    fun logoutError()
}