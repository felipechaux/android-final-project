package com.felipechauxlab.yourfarmerapp.presenter

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yourfarmerapp.R
import com.felipechauxlab.yourfarmerapp.restApi.ConstantsRestApi
import com.felipechauxlab.yourfarmerapp.restApi.RestApiAdapter
import com.felipechauxlab.yourfarmerapp.restApi.dto.PublishProductDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.request.RequestPublishProductDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.response.ResponseDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.response.ResponsePhotoDTO
import com.felipechauxlab.yourfarmerapp.restApi.dto.response.ResponsePublishProductsDTO
import com.felipechauxlab.yourfarmerapp.view.fragment.IProductsFragmentView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class PublishFragmentPresenter: ViewModel() ,IPublishFragmentPresenter {

    private val _showOrHideLoader = MutableLiveData<Boolean>()
    private val _uploadedPhotoSuccess = MutableLiveData<String>()
    private var products: List<PublishProductDTO?>? = null
    private var iProductsV: IProductsFragmentView?=null

    val showOrHideLoader: LiveData<Boolean>
        get() = _showOrHideLoader

    val uploadedPhotoSuccess: LiveData<String>
        get() =_uploadedPhotoSuccess

    fun uploadPhoto(context: Context, file: File) {
        _showOrHideLoader.value = true
        val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestFile)
        RestApiAdapter.build()?.uploadPhoto(body)?.enqueue(
                object : Callback<ResponsePhotoDTO> {
                    override fun onFailure(call: Call<ResponsePhotoDTO>, t: Throwable) {
                        _showOrHideLoader.value = false
                        Toast.makeText(context, context.getString(R.string.error_has_occurred), Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<ResponsePhotoDTO>, response: Response<ResponsePhotoDTO>) {
                        _showOrHideLoader.value = false
                        if (response.code() == 200) {
                            _uploadedPhotoSuccess.value = response.body()?.secureUrl.toString()
                            Toast.makeText(context, context.getString(R.string.product_image_uploaded), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, context.getString(R.string.error_has_occurred), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        )
    }

    private fun postPublishProduct(context: Context, requestPublishProductDTO: RequestPublishProductDTO) {
        _showOrHideLoader.value = true
        RestApiAdapter.build()?.postPublishProduct(requestPublishProductDTO)?.enqueue(
                object : Callback<ResponseDTO> {
                    override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                        _showOrHideLoader.value = false
                        Toast.makeText(context, context.getString(R.string.error_has_occurred), Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                        val res = response.body()
                        _showOrHideLoader.value = false
                        if (res?.code == ConstantsRestApi.CODE_SUCCESS) {
                            Toast.makeText(context, context.getString(R.string.product_published), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, context.getString(R.string.error_has_occurred), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        )
    }


    fun validatePublishProduct(context: Context, photoUrl: String?, name: String?, description: String, cost: String?, quantity: Int){
      if (photoUrl!=null  && name!=null && cost?.isNotEmpty() == true) {
          val createRequestPublishProduct = RequestPublishProductDTO().apply {
              this.productName=name
              this.productDescription=description
              this.productCost=cost.toLong()
              this.productPhoto=photoUrl
              this.productQuantity=quantity
          }
          postPublishProduct(context, createRequestPublishProduct)
      }else{
          Toast.makeText(context, context.getString(R.string.complete_some_publish_fields), Toast.LENGTH_SHORT).show()
      }
    }

     fun bitmapToFile(context: Context, bitmap: Bitmap): File {
        val wrapper = ContextWrapper(context)
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")
        try{
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }
        return file
    }

    override fun getPublishProducts(context: Context,iProductsFragmentView: IProductsFragmentView) {
        iProductsV=iProductsFragmentView
        _showOrHideLoader.value = true
        RestApiAdapter.build()?.getPublishProducts()?.enqueue(
                object : Callback<ResponsePublishProductsDTO> {
                    override fun onFailure(call: Call<ResponsePublishProductsDTO>, t: Throwable) {
                        _showOrHideLoader.value = false
                        Toast.makeText(context, context.getString(R.string.error_has_occurred), Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<ResponsePublishProductsDTO>, response: Response<ResponsePublishProductsDTO>) {
                        val res = response.body()
                        _showOrHideLoader.value = false
                        if (res?.code == ConstantsRestApi.CODE_SUCCESS) {
                            products= res.publishProducts
                            showPublishProducts()
                        } else {
                            Toast.makeText(context, context.getString(R.string.error_has_occurred), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        )
    }

    override fun showPublishProducts() {
        iProductsV?.createAdapter(products)?.let { iProductsV?.initAdapterMyProducts(it) }
        iProductsV?.generateGridLayout()
    }

}