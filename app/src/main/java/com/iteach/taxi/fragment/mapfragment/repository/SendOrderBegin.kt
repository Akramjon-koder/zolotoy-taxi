package com.iteach.taxi.fragment.mapfragment.repository

import androidx.lifecycle.MutableLiveData
import com.iteach.taxi.api.NetworkManeger
import com.iteach.taxi.fragment.login.base.LoginModel
import com.iteach.taxi.fragment.login.base.Login_Password
import com.iteach.taxi.fragment.mapfragment.base.DefinitionModel
import com.iteach.taxi.fragment.mapfragment.base.OrderBeginModel
import com.iteach.taxi.model.BaseResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class SendOrderBegin {
    val compositeDisposible = CompositeDisposable()
    fun sendOrderStart(error: MutableLiveData<String>, success: MutableLiveData<OrderBeginModel>, definitionModel: DefinitionModel,token:String){

        compositeDisposible.add(
            NetworkManeger
                .getApiService().senddefinition(token,definitionModel).
            subscribeOn(Schedulers.io()).
            observeOn(AndroidSchedulers.mainThread()).
            subscribeWith(object : DisposableObserver<BaseResponse<OrderBeginModel>>(){
                override fun onNext(t: BaseResponse<OrderBeginModel>) {
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