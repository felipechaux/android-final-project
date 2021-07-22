package com.felipechauxlab.yourfarmerapp.restApi

import com.felipechauxlab.yourfarmerapp.restApi.dto.request.RequestAuthUserDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.request.RequestPublishProductDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.request.RequestRegisterUserDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.response.ResponseDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.response.ResponsePhotoDTO
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface YourFarmerService {

    @POST(ConstantsRestApi.POST_REGISTER_USER)
    fun postRegisterUser(@Body requestRegisterUserDTO: RequestRegisterUserDTO): Call<ResponseDTO>

    @POST(ConstantsRestApi.POST_AUTH_USER)
    fun postAuthUser(@Body requestAuthUserDTO: RequestAuthUserDTO): Call<ResponseDTO>

    @Multipart
    @POST(ConstantsRestApi.UPLOAD_PHOTO)
    fun uploadPhoto( @Part file: MultipartBody.Part): Call<ResponsePhotoDTO>

    @POST(ConstantsRestApi.POST_PUBLISH_PRODUCT)
    fun postPublishProduct(@Body requestPublishProductDTO: RequestPublishProductDTO): Call<ResponseDTO>

}