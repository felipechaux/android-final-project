package com.felipechauxlab.yourfarmerapp.restApi.dto.request

import com.google.gson.annotations.SerializedName

data class RequestPublishProductDTO(
    @SerializedName("idProduct") var idProduct: String? = null,
    @SerializedName("productName") var productName: String? = null,
    @SerializedName("productDescription") var productDescription: String? = null,
    @SerializedName("productCost") var productCost: Long? = null,
    @SerializedName("productPhoto") var productPhoto: String? = null,
    @SerializedName("productQuantity") var productQuantity: Int? = null
)