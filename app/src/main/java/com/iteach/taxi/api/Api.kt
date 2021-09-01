package com.iteach.taxi.api


import com.iteach.taxi.fragment.login.base.Login_Password
import com.iteach.taxi.model.BaseResponse
import com.iteach.taxi.model.LogindataModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    @POST(Api_urls.LOGIN_URL)
fun getLogin(
        @Body loginpassword: Login_Password
): Observable<BaseResponse<LogindataModel>>

}