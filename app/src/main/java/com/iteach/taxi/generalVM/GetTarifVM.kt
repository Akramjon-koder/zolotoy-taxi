package com.iteach.taxi.generalVM

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iteach.taxi.fragment.addcab.base.CabListRepo
import com.iteach.taxi.generalrepo.GetTarifRepo
import com.iteach.taxi.model.GetTarifModel

class GetTarifVM : ViewModel() {
    val listrepo = GetTarifRepo()
    val error = MutableLiveData<String>()
    val getTarifLiveData = MutableLiveData<GetTarifModel>()
    fun getTarif(token:String,car_id:Int) {
        listrepo.getTarif(error, getTarifLiveData,token,car_id)
    }
}