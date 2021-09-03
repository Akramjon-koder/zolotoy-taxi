package com.iteach.taxi.api


import com.iteach.taxi.fragment.login.base.Login_Password
import com.iteach.taxi.model.BaseResponse
import com.iteach.taxi.model.LogindataModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @POST(Api_urls.LOGIN_URL)
    @FormUrlEncoded
fun getLogin(
        @Field("username") username:String,
        @Field("password") pass:String

): Observable<BaseResponse<LogindataModel>>

}