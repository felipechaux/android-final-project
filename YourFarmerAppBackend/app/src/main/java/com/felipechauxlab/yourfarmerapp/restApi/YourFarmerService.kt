package com.felipechauxlab.yourfarmerapp.restApi

import com.felipechauxlab.yourfarmerapp.restApi.dto.request.RequestAuthUserDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.request.RequestRegisterUserDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.response.ResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface YourFarmerService {

    @POST(ConstantsRestApi.POST_REGISTER_USER)
    fun postRegisterUser(@Body requestRegisterUserDTO: RequestRegisterUserDTO): Call<ResponseDTO>

    @POST(ConstantsRestApi.POST_AUTH_USER)
    fun postAuthUser(@Body requestAuthUserDTO: RequestAuthUserDTO): Call<ResponseDTO>
}