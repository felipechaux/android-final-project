package com.felipechauxlab.yourfarmerapp.restApi.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PublishProductDTO(
    @SerializedName("productKey") var productKey: String? = null,
    @SerializedName("productCost") var productCost: String? = null,
    @SerializedName("productName") var productName: String? = null,
    @SerializedName("productDescription") var productDescription: String? = null,
    @SerializedName("productPhoto") var productPhoto: String? = null,
    @SerializedName("productQuantity") var productQuantity: Int? = null,
    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null

) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Double::class.java.classLoader) as? Double, parcel.readValue(Double::class.java.classLoader) as? Double
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productKey)
        parcel.writeString(productCost)
        parcel.writeString(productName)
        parcel.writeString(productDescription)
        parcel.writeString(productPhoto)
        parcel.writeValue(productQuantity)
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PublishProductDTO> {
        override fun createFromParcel(parcel: Parcel): PublishProductDTO {
            return PublishProductDTO(parcel)
        }

        override fun newArray(size: Int): Array<PublishProductDTO?> {
            return arrayOfNulls(size)
        }
    }
}