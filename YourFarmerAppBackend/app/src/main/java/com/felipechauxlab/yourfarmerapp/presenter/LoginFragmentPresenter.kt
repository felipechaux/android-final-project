package com.felipechauxlab.yourfarmerapp.presenter

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yourfarmerapp.R
import com.felipechauxlab.yourfarmerapp.entities.User
import com.felipechauxlab.yourfarmerapp.restApi.ConstantsRestApi
import com.felipechauxlab.yourfarmerapp.restApi.RestApiAdapter
import com.felipechauxlab.yourfarmerapp.restApi.dto.request.RequestAuthUserDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.request.RequestRegisterUserDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.response.ResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragmentPresenter : ViewModel() {

    private val _showOrHideLoader = MutableLiveData<Boolean>()
    private val _goToIntro = MutableLiveData<Boolean>()
    private val _goToMap = MutableLiveData<Boolean>()

    val showOrHideLoader: LiveData<Boolean>
        get() = _showOrHideLoader

    val goToIntro: LiveData<Boolean>
        get() = _goToIntro

    val goToMap: LiveData<Boolean>
        get() = _goToMap


    fun registerUser(context: Context, user: User) {
        val createRequestRegisterUserDTO = RequestRegisterUserDTO().apply {
            this.userName = user.userName
            this.identification = user.identification
            this.password = user.password
            this.phoneNumber = user.phoneNumber.toString()
            this.isFarmer = user.isFarmer
        }
        postRegisterUser(context, createRequestRegisterUserDTO)
    }

    fun authUser(context: Context, user: User) {
        if (user.userName?.isNotEmpty() == true && user.password?.isNotEmpty() == true) {
            val createRequestAuthUserDTO = RequestAuthUserDTO().apply {
                this.userName = user.userName
                this.password = user.password
            }
            postAuthUser(context, createRequestAuthUserDTO)
        } else {
            Toast.makeText(context, context.getString(R.string.complete_all_fields), Toast.LENGTH_SHORT).show()
        }
    }

    private fun postRegisterUser(context: Context, requestRegisterUserDTO: RequestRegisterUserDTO) {
        _showOrHideLoader.value = true
        RestApiAdapter.build()?.postRegisterUser(requestRegisterUserDTO)?.enqueue(
                object : Callback<ResponseDTO> {
                    override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                        Toast.makeText(context, context.getString(R.string.error_has_occurred), Toast.LENGTH_SHORT).show()
                        _showOrHideLoader.value = false
                    }

                    override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                        val res = response.body()
                        _showOrHideLoader.value = false
                        if (res?.code == ConstantsRestApi.CODE_SUCCESS) {
                            Toast.makeText(context, context.getString(R.string.register_user_success), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, context.getString(R.string.error_has_occurred), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        )
    }

    private fun postAuthUser(context: Context, requestAuthUserDTO: RequestAuthUserDTO) {
        _showOrHideLoader.value = true
        RestApiAdapter.build()?.postAuthUser(requestAuthUserDTO)?.enqueue(
                object : Callback<ResponseDTO> {
                    override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                        _showOrHideLoader.value = false
                        Toast.makeText(context, context.getString(R.string.error_has_occurred), Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                        val res = response.body()
                        _showOrHideLoader.value = false
                        if (res?.code == ConstantsRestApi.CODE_SUCCESS) {
                            if(res.user?.isFarmer == true){
                                _goToIntro.value = true
                            }else{
                                _goToMap.value = true
                            }
                        } else {
                            Toast.makeText(context, context.getString(R.string.user_not_found), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        )
    }


}