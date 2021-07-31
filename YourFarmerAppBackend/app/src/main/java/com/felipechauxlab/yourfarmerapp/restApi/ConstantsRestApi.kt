package com.felipechauxlab.yourfarmerapp.restApi

object ConstantsRestApi {
    const val TIMEOUT_SERVICES: Long = 300
    const val CODE_SUCCESS: Int = 0
    const val BASE_URL = "https://gentle-fjord-74492.herokuapp.com/"
    const val ACCESS_TOKEN =
        "&access_token=IGQVJXX0hpa2Ryb2tQRnBCdHFvdDk4OGllM0YweUtLVWFjWFQxYTBlRTFvTzdtMDhpaHpKX0EtN2lyY2NDMzA1eTdsUU1XeFFabGhNSTRQVkg0eGV6Ynlud2xFdEV0SV9SdnJySDh0V29QWnJQQ0s5VAZDZD"
    const val KEY_GET_RECENT_INFO = "me/media?fields=id,media_url,media_type"

    const val FIREBASE_SERVER_URL = "https://agile-tor-51409.herokuapp.com/"
    const val POST_REGISTER_USER = "register-user"
    const val POST_AUTH_USER = "auth-user"
    const val POST_PUBLISH_PRODUCT = "publish-product"
    const val POST_EDIT_PRODUCT = "edit-product"
    const val GET_PUBLISH_PRODUCTS = "products"
    const val UPLOAD_PHOTO = " https://api.cloudinary.com/v1_1/dpyqmhhmi/image/upload?upload_preset=t3ihtvhc"
}