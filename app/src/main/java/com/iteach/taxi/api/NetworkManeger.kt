package com.iteach.taxi.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManeger {


    var retrofit :Retrofit? = null

    var api :Api?= null

    fun getApiService() :Api{
        if(api == null){
            retrofit = Retrofit.Builder().
            addConverterFactory(GsonConverterFactory.create()).
            addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
            baseUrl(Api_urls.BASE_URL).
            build()

            api = retrofit!!.create(Api::class.java)
        }
        return api!!
    }

}