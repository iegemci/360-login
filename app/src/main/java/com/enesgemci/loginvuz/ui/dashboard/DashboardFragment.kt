package com.enesgemci.loginvuz.ui.dashboard

import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.enesgemci.loginvuz.R
import com.enesgemci.loginvuz.core.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment :
    BaseFragment<DashboardView, DashboardPresenter>(R.layout.fragment_dashboard), DashboardView {

    override fun onFragmentCreated() {
        presenter.userLiveData.observe(viewLifecycleOwner, Observer {
            userNameTextView.text =
                getString(R.string.user_name_placeholder, it.firstName, it.lastName)
        })

        logoutButton.setOnClickListener {
            logout()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    logout()
                }
            })
    }

    private fun logout() {
        presenter.logout()
    }

    override fun logoutError() {
        // ignore error and redirect to login page
        redirectToLogin()
    }

    override fun logoutSuccess() {
        redirectToLogin()
    }

    private fun redirectToLogin() {
        findNavController().popBackStack()
    }
}