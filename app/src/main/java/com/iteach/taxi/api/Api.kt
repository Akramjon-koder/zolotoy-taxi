package com.iteach.taxi.api


import com.iteach.taxi.fragment.login.base.LoginModel
import com.iteach.taxi.fragment.register.base.RegisterModel
import com.iteach.taxi.fragment.register.base.SendCode
import com.iteach.taxi.fragment.signup.base.Send_Register_Model
import com.iteach.taxi.model.BaseResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface Api {
    @POST(Api_urls.LOGIN_URL)
    @FormUrlEncoded
    fun getLogin(
        @Field("username") username: String,
        @Field("password") pass: String

    ): Observable<BaseResponse<LoginModel>>

    @POST(Api_urls.REGISTR_URL)
    fun sendRegistr(
        @Body sendRegisterModel: Send_Register_Model

    ): Observable<BaseResponse<RegisterModel>>

    @POST(Api_urls.REGISTR_CODE_URL)
    fun sendRegistrCode(
        @Body sendCode: SendCode
    ): Observable<BaseResponse<LoginModel>>
}