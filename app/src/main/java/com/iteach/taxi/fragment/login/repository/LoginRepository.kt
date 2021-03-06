package com.iteach.taxi.fragment.login.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.iteach.taxi.api.NetworkManeger
import com.iteach.taxi.fragment.login.base.LoginModel
import com.iteach.taxi.fragment.login.base.Login_Password
import com.iteach.taxi.model.BaseResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginRepository {
    val compositeDisposible = CompositeDisposable()
    fun sendLogin(error: MutableLiveData<String>,success: MutableLiveData<LoginModel> ,logginpassword:Login_Password){

        compositeDisposible.add(
            NetworkManeger.getApiService().getLogin(logginpassword.username.toString(),logginpassword.password.toString()).
            subscribeOn(Schedulers.io()).
            observeOn(AndroidSchedulers.mainThread()).
            subscribeWith(object : DisposableObserver<BaseResponse<LoginModel>>(){
                override fun onNext(t: BaseResponse<LoginModel>) {
                    if (t?.success == true) {
                        success.value = t?.data
                    } else
                        error.value = t?.message
                }
                override fun onError(e: Throwable) {
                    error.value = e?.localizedMessage
                }
                override fun onComplete() {
                }

            })
        )
    }
}