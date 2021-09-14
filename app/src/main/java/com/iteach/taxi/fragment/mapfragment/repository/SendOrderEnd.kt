package com.iteach.taxi.fragment.mapfragment.repository

import androidx.lifecycle.MutableLiveData
import com.iteach.taxi.api.NetworkManeger
import com.iteach.taxi.fragment.mapfragment.base.EndDefinitionModel
import com.iteach.taxi.fragment.mapfragment.base.OrderEndModel
import com.iteach.taxi.model.BaseResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
class SendOrderEnd {
    val compositeDisposible = CompositeDisposable()
    fun sendOrderStart(
        error: MutableLiveData<String>,
        success: MutableLiveData<OrderEndModel>,
        definitionModelEnd: EndDefinitionModel,
        token: String
    ) {
        compositeDisposible.add(
            NetworkManeger
                .getApiService()
                .senddefinitionend(token, definitionModelEnd).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<OrderEndModel>>() {
                    override fun onNext(t: BaseResponse<OrderEndModel>) {
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