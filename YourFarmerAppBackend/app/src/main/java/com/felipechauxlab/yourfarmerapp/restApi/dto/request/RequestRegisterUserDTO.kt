package com.felipechauxlab.yourfarmerapp.restApi.dto.request

import com.google.gson.annotations.SerializedName

data class RequestRegisterUserDTO(
    @SerializedName("userName") var userName: String? = null,
    @SerializedName("identification") var identification: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("phoneNumber") var phoneNumber: String? = null,
    @SerializedName("isFarmer") var isFarmer: Boolean? = null
)