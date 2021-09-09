package com.iteach.taxi.fragment.register.repository

import androidx.lifecycle.MutableLiveData
import com.iteach.taxi.api.NetworkManeger
import com.iteach.taxi.fragment.login.base.LoginModel
import com.iteach.taxi.fragment.register.base.SendCode
import com.iteach.taxi.model.BaseResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class RegisterRepository {
    val compositeDisposible = CompositeDisposable()
    fun sendRegistrCode(
        error: MutableLiveData<String>,
        success: MutableLiveData<LoginModel>,
        sendCode: SendCode
    ) {

        compositeDisposible.add(
            NetworkManeger.getApiService()
                .sendRegistrCode(sendCode)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<LoginModel>>() {
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