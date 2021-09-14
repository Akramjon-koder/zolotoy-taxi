package com.iteach.taxi.fragment.addcab.base
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iteach.taxi.fragment.login.base.Login_Password
import okhttp3.RequestBody

class CabListVM : ViewModel() {
    val listrepo = CabListRepo()
    val user = MutableLiveData<ArrayList<CabListModel>>()
    val error = MutableLiveData<String>()
    val changeLiveData = MutableLiveData<ArrayList<CabListModel>>()
    val carModelLiveData = MutableLiveData<ArrayList<CarTypeModel>>()
    val addCabLiveData = MutableLiveData<CabListModel>()
    fun getCabList(token: String) {
        listrepo.getCabList(error, user, token)
    }
    fun changeStatus(token: String, carid: Int, status: Int) {
        listrepo.setActiveCab(error, changeLiveData, token, carid, status)
    }
    fun getCarTypeModel(token: String) {
        listrepo.getCarType(error, carModelLiveData, token)
    }
    fun addCab(token: String, body: RequestBody) {
        listrepo.addCab(error, addCabLiveData, token, body)
    }
}