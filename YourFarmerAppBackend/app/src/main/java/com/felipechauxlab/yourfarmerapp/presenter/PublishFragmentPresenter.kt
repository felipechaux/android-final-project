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
    private val _successProductUpdated = MutableLiveData<Boolean>()
    private val _successProductRemoved = MutableLiveData<Boolean>()
    private val _uploadedPhotoSuccess = MutableLiveData<String?>()
    private val _latitude = MutableLiveData<Float?>()
    private val _longitude = MutableLiveData<Float?>()
    private var products: List<PublishProductDTO?>? = null
    private var iProductsV: IProductsFragmentView?=null

    val showOrHideLoader: LiveData<Boolean>
        get() = _showOrHideLoader

    val successProductUpdated: LiveData<Boolean>
        get() = _successProductUpdated

    val successProductRemoved: LiveData<Boolean>
        get() = _successProductRemoved

    val uploadedPhotoSuccess: LiveData<String?>
        get() =_uploadedPhotoSuccess


    fun uploadPhoto(context: Context, file: File) {
        val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestFile)
        RestApiAdapter.build()?.uploadPhoto(body)?.enqueue(
                object : Callback<ResponsePhotoDTO> {
                    override fun onFailure(call: Call<ResponsePhotoDTO>, t: Throwable) {
                        Toast.makeText(context, context.getString(R.string.error_has_occurred), Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<ResponsePhotoDTO>, response: Response<ResponsePhotoDTO>) {
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

    fun editProduct(context: Context,productDTO: PublishProductDTO?){
        if (productDTO?.productName?.isNotEmpty() == true && productDTO.productCost?.isNotEmpty() == true && productDTO.productDescription?.isNotEmpty() == true) {
            val createRequestPublishProduct = RequestPublishProductDTO().apply {
                this.idProduct= productDTO.productKey
                this.productName= productDTO.productName
                this.productDescription= productDTO.productDescription
                this.productCost= productDTO.productCost?.toLong()
                this.productPhoto= productDTO.productPhoto
                this.productQuantity= productDTO.productQuantity
            }
            postEditProduct(context, createRequestPublishProduct)
        }else{
            Toast.makeText(context, context.getString(R.string.complete_all_fields), Toast.LENGTH_SHORT).show()
        }
    }

    fun deletePublishProduct(context: Context, idProduct: String){
        deleteProduct(context,idProduct)
    }

    private fun postEditProduct(context: Context, requestPublishProductDTO: RequestPublishProductDTO) {
        _showOrHideLoader.value = true
        RestApiAdapter.build()?.postEditProduct(requestPublishProductDTO)?.enqueue(
            object : Callback<ResponseDTO> {
                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                    _showOrHideLoader.value = false
                    Toast.makeText(context, context.getString(R.string.error_has_occurred), Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    val res = response.body()
                    _showOrHideLoader.value = false
                    if (res?.code == ConstantsRestApi.CODE_SUCCESS) {
                        _successProductUpdated.value=true
                        Toast.makeText(context, context.getString(R.string.product_updated), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, context.getString(R.string.error_has_occurred), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }


    private fun deleteProduct(context: Context, id:String) {
        _showOrHideLoader.value = true
        RestApiAdapter.build()?.deleteProduct(id)?.enqueue(
                object : Callback<ResponseDTO> {
                    override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                        _showOrHideLoader.value = false
                        Toast.makeText(context, context.getString(R.string.error_has_occurred), Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                        val res = response.body()
                        _showOrHideLoader.value = false
                        if (res?.code == ConstantsRestApi.CODE_SUCCESS) {
                            _successProductRemoved.value=true
                            Toast.makeText(context, context.getString(R.string.product_removed), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, context.getString(R.string.error_has_occurred), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        )
    }

    fun setCurrentLocation(latitude:Float?, longitude:Float?){
        _latitude.value=latitude
        _longitude.value=longitude
    }

    fun validatePublishProduct(context: Context, photoUrl: String?, name: String?, description: String, cost: String?, quantity: Int){
      if (photoUrl!=null  && name!=null && cost?.isNotEmpty() == true) {
          val createRequestPublishProduct = RequestPublishProductDTO().apply {
              this.productName=name
              this.productDescription=description
              this.productCost=cost.toLong()
              this.productPhoto=photoUrl
              this.productQuantity=quantity
              this.latitude=_latitude.value
              this.longitude=_longitude.value
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

    fun resetValues(){
        _uploadedPhotoSuccess.value=null
    }

}