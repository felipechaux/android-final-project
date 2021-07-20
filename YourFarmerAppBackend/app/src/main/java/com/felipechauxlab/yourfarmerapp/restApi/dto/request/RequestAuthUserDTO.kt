package com.felipechauxlab.yourfarmerapp.restApi.dto.request

import com.google.gson.annotations.SerializedName

data class RequestAuthUserDTO(
    @SerializedName("userName") var userName: String? = null,
    @SerializedName("password") var password: String? = null
)