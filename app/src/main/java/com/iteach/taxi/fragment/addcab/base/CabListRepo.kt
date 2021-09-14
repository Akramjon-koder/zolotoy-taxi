package com.iteach.taxi.fragment.addcab.base
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.iteach.taxi.api.NetworkManeger
import com.iteach.taxi.fragment.login.base.Login_Password
import com.iteach.taxi.model.BaseResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.RequestBody
class CabListRepo {
    val compositeDisposible = CompositeDisposable()
    fun getCabList(error: MutableLiveData<String>, success: MutableLiveData<ArrayList<CabListModel>>,token:String){
         compositeDisposible.add(
            NetworkManeger.getApiService()
                .getCabList(token).
            subscribeOn(Schedulers.io()).
            observeOn(AndroidSchedulers.mainThread()).
            subscribeWith(object : DisposableObserver<BaseResponse<ArrayList<CabListModel>>>(){
                override fun onNext(t: BaseResponse<ArrayList<CabListModel>>) {
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

    fun setActiveCab(error: MutableLiveData<String>,success: MutableLiveData<ArrayList<CabListModel>>,
                     token:String,carId:Int,status:Int){
        compositeDisposible.add(
            NetworkManeger.getApiService()
                .changeStatus(token,carId,status).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeWith(object : DisposableObserver<BaseResponse<ArrayList<CabListModel>>>(){
                    override fun onNext(t: BaseResponse<ArrayList<CabListModel>>) {
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
    fun getCarType(error: MutableLiveData<String>,success: MutableLiveData<ArrayList<CarTypeModel>>,token:String){
        compositeDisposible.add(
            NetworkManeger.getApiService()
                .getCarModels(token).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeWith(object : DisposableObserver<BaseResponse<ArrayList<CarTypeModel>>>(){
                    override fun onNext(t: BaseResponse<ArrayList<CarTypeModel>>) {
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
    fun addCab(error: MutableLiveData<String>,success: MutableLiveData<CabListModel>,token:String,body: RequestBody){
        compositeDisposible.add(
            NetworkManeger.getApiService()
                .addCab(token,body).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeWith(object : DisposableObserver<BaseResponse<CabListModel>>(){
                    override fun onNext(t: BaseResponse<CabListModel>) {
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