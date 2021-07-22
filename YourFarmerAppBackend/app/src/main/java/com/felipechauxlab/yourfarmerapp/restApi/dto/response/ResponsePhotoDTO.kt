package com.felipechauxlab.yourfarmerapp.restApi.dto.response

import com.google.gson.annotations.SerializedName

data class ResponsePhotoDTO(
        @SerializedName("secure_url") var secureUrl: String? = null,
        @SerializedName("url") var url: String? = null
)