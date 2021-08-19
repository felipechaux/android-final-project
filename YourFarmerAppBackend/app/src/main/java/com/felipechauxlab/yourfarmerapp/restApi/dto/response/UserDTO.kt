package com.felipechauxlab.yourfarmerapp.restApi.dto.response

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("identification") var identification: String? = null,
    @SerializedName("isFarmer") var isFarmer: Boolean? = null,
    @SerializedName("phoneNumber") var phoneNumber: String? = null,
    @SerializedName("userName") var userName: String? = null
)