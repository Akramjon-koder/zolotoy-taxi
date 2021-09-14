package com.iteach.taxi.generalrepo

import androidx.lifecycle.MutableLiveData
import com.iteach.taxi.api.NetworkManeger
import com.iteach.taxi.fragment.register.base.RegisterModel
import com.iteach.taxi.fragment.signup.base.Send_Register_Model
import com.iteach.taxi.model.BaseResponse
import com.iteach.taxi.model.GetTarifModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class GetTarifRepo {
    val compositeDisposible = CompositeDisposable()
    fun getTarif(
        error: MutableLiveData<String>,
        success: MutableLiveData<GetTarifModel>,
        token:String,
        car_id:Int
    )
    {
        compositeDisposible.add(
            NetworkManeger.getApiService()
                .getTarifModel(token, car_id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<GetTarifModel>>() {
                    override fun onNext(t: BaseResponse<GetTarifModel>) {
                        if (t?.success == true) {
                            success.value = t?.data
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