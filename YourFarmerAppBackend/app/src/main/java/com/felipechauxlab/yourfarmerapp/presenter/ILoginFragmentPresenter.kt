package com.felipechauxlab.yourfarmerapp.presenter

import com.felipechauxlab.yourfarmerapp.restApi.dto.request.RequestRegisterUserDTO

interface ILoginFragmentPresenter {
    fun postRegisterUser(requestRegisterUserDTO: RequestRegisterUserDTO):RequestRegisterUserDTO
}