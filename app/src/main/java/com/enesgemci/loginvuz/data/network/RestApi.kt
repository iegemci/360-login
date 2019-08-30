package com.enesgemci.loginvuz.data.network

import com.enesgemci.loginvuz.data.network.model.api.LoginRequest
import com.enesgemci.loginvuz.data.network.model.api.LoginResponse
import com.enesgemci.loginvuz.data.network.model.api.LogoutResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface RestApi {

    @POST("login")
    fun login(@Body request: LoginRequest): Single<LoginResponse>

    @POST("logout")
    fun logout(): Single<LogoutResponse>
}