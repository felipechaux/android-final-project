package com.felipechauxlab.yourfarmerapp.restApi

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestApiAdapter {

    init {
        setup()
    }

    companion object {
        // Timeout usado por defecto al momento de llamar los servicios
        private val defaultTimeOut: Long = ConstantsRestApi.TIMEOUT_SERVICES
        private var retrofit: Retrofit.Builder? = null

        fun build(timeOut: Long = defaultTimeOut): YourFarmerService? {
            return setup(timeOut)
                ?.build()?.create(YourFarmerService::class.java)
        }

        private fun setup(timeOut: Long = defaultTimeOut): Retrofit.Builder? {
            retrofit = Retrofit.Builder()

            val lenient = GsonBuilder().setLenient().create()
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val clientBuilder = OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .addInterceptor(logging)

            retrofit?.client(clientBuilder.build())
                ?.baseUrl(ConstantsRestApi.BASE_URL)
                ?.addConverterFactory(GsonConverterFactory.create(lenient))
            return retrofit
        }
    }
}

