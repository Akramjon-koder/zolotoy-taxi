package com.iteach.taxi.fragment.register.repository

import androidx.lifecycle.MutableLiveData
import com.iteach.taxi.api.NetworkManeger
import com.iteach.taxi.model.BaseResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class ResendSMS {
    val compositeDisposible = CompositeDisposable()
    fun resendSMS(
        error: MutableLiveData<String>,
        SMS: MutableLiveData<Boolean>,
        phone: String,
    ){
        compositeDisposible.add(
            NetworkManeger.getApiService()
                .resendRegistrCode(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<Unit>>(){
                    override fun onNext(t: BaseResponse<Unit>) {
                        if (t?.success == true) {
                            SMS.value =t?.success
                        } else{
                            error.value = t?.message
                        }

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