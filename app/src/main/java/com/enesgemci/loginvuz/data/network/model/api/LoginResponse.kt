package com.enesgemci.loginvuz.data.network.model.api

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("lastName") val lastName: String?
)