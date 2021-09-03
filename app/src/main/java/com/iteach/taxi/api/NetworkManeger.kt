package com.iteach.taxi.api

import com.iteach.taxi.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManeger {

    private val client = OkHttpClient.Builder().also { client ->
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logging)}
    }.build()

    var retrofit :Retrofit? = null
    var api :Api?= null

    fun getApiService() :Api{
        if(api == null){
            retrofit = Retrofit.Builder().
            addConverterFactory(GsonConverterFactory.create()).
            addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
            client(client).
            baseUrl(Api_urls.BASE_URL).
            build()

            api = retrofit!!.create(Api::class.java)
        }
        return api!!
    }
}