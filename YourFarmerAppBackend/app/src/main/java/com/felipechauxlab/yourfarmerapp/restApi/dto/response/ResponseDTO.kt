package com.felipechauxlab.yourfarmerapp.restApi.dto.response

import com.google.gson.annotations.SerializedName

data class ResponseDTO(
    @SerializedName("message") var message: String? = null,
    @SerializedName("code") var code: Int? = null,
    @SerializedName("user") var user: UserDTO? = null
)