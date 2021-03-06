package com.felipechauxlab.yourfarmerapp.restApi

import com.felipechauxlab.yourfarmerapp.restApi.dto.request.RequestAuthUserDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.request.RequestPublishProductDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.request.RequestRegisterUserDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.response.ResponseDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.response.ResponsePhotoDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.response.ResponsePublishProductsDTO
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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

    @POST(ConstantsRestApi.POST_EDIT_PRODUCT)
    fun postEditProduct(@Body requestPublishProductDTO: RequestPublishProductDTO): Call<ResponseDTO>

    @DELETE(ConstantsRestApi.DELETE_PRODUCT)
    fun deleteProduct(@Path("id") id: String): Call<ResponseDTO>

    @GET(ConstantsRestApi.GET_PUBLISH_PRODUCTS)
    fun getPublishProducts(): Call<ResponsePublishProductsDTO>

}