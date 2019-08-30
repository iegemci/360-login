package com.enesgemci.loginvuz.ui.login

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.enesgemci.loginvuz.R
import com.enesgemci.loginvuz.core.base.BaseFragment
import com.enesgemci.loginvuz.core.extension.afterTextChanged
import com.enesgemci.loginvuz.view.LoginButton
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<LoginView, LoginPresenter>(R.layout.fragment_login), LoginView {

    private val email: String
        get() = emailEditText.text.toString()

    private val password: String
        get() = passwordEditText.text.toString()

    override fun onFragmentCreated() {
        loginButton.setOnClickListener {
            presenter.login(email, password)
        }

        emailEditText.afterTextChanged {
            emailTextInputLayout.error = null
        }

        passwordEditText.afterTextChanged {
            passwordTextInputLayout.error = null
        }

        loginButton.onSuccess {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDashboardFragment())
        }

        loginButton.state = LoginButton.State.Default
    }

    override fun onEmailValidationError() {
        emailTextInputLayout.error = getString(R.string.invalid_username)
    }

    override fun onPasswordValidationError() {
        passwordTextInputLayout.error = getString(R.string.invalid_password)
    }

    override fun onLoginFailed() {
        loginButton.state = LoginButton.State.Default
        Toast.makeText(requireContext(), R.string.login_failed, Toast.LENGTH_SHORT).show()
    }

    override fun onLoginSuccess() {
        loginButton.state = LoginButton.State.Success
    }

    override fun loginOnProgress() {
        loginButton.state = LoginButton.State.Loading
    }
}
