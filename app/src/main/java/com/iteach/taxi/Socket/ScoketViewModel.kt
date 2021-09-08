package uz.algorithmgateway.project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONArray

import org.json.JSONObject

class ScoketViewModel:ViewModel() {
  val jsonarray =MutableLiveData<JSONObject>()
    fun setJsonArray(array: JSONObject){
        return jsonarray.postValue(array) }
    val realjsonArray=MutableLiveData<JSONArray>()
    fun setrealJsonArray(array:JSONArray){
        return realjsonArray.postValue(array)
    }
}