package com.felipechauxlab.yourfarmerapp.entities

data class User(
    var userName: String? = null,
    var identification: String? = null,
    var password: String? = null,
    var phoneNumber: Int? = null,
    var isFarmer: Boolean? = null
)