package com.iteach.taxi.fragment.signup.repository

import androidx.lifecycle.MutableLiveData
import com.iteach.taxi.api.NetworkManeger
import com.iteach.taxi.fragment.register.base.RegisterModel
import com.iteach.taxi.fragment.signup.base.Send_Register_Model
import com.iteach.taxi.model.BaseResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.security.AccessController.getContext
class SignUpRepository {
    val compositeDisposible = CompositeDisposable()
    fun sendSignUp(
        error: MutableLiveData<String>,
        success: MutableLiveData<RegisterModel>,
        sendRegisterModel: Send_Register_Model)
    {
        compositeDisposible.add(
            NetworkManeger.getApiService()
                .sendRegistr(sendRegisterModel)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<RegisterModel>>() {
                    override fun onNext(t: BaseResponse<RegisterModel>) {
                        if (t?.success == true) {
                            success.value = t?.data
                        } else{
                            error.value = t?.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e?.message
                    }

                    override fun onComplete() {

                    }

                })
        )

    }
}