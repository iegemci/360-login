package com.enesgemci.loginvuz.data.network

import com.enesgemci.loginvuz.core.base.BaseInteractor
import com.enesgemci.loginvuz.core.extension.orFalse
import com.enesgemci.loginvuz.data.network.model.api.LoginRequest
import com.enesgemci.loginvuz.data.persistent.User
import com.enesgemci.loginvuz.data.persistent.UserRepository
import io.reactivex.Single
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginInteractor @Inject constructor(
    private val restApi: RestApi,
    private val userRepository: UserRepository
) : BaseInteractor() {

    fun login(username: String, password: String): Single<User> {
        return restApi.login(LoginRequest(username, password))
            .map { User(it.firstName.orEmpty(), it.lastName.orEmpty()) }
            .doOnSuccess {
                launch {
                    userRepository.saveUser(it)
                }
            }
    }

    fun logout(): Single<Boolean> {
        return restApi.logout()
            .map { it.success.orFalse() }
            .doOnSuccess {
                launch { userRepository.deleteUsers() }
            }
    }

    fun getUser() = userRepository.getUser()
}