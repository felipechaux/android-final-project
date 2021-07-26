package com.felipechauxlab.yourfarmerapp.restApi.dto.response

import com.felipechauxlab.yourfarmerapp.restApi.dto.PublishProductDTO
import com.google.gson.annotations.SerializedName

data class ResponsePublishProductsDTO(
    @SerializedName("message") var message: String? = null,
    @SerializedName("code") var code: Int? = null,
    @SerializedName("products") var publishProducts: List<PublishProductDTO?>? = null
)