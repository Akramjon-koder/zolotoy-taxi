package com.iteach.taxi.api
import com.iteach.taxi.fragment.addcab.base.CabListModel
import com.iteach.taxi.fragment.addcab.base.CarTypeModel
import com.iteach.taxi.model.BaseResponse
import com.iteach.taxi.model.LogindataModel
import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import retrofit2.http.*
interface Api {
    @POST(Api_urls.LOGIN_URL)
    @FormUrlEncoded
    fun getLogin(
        @Field("username") username: String,
        @Field("password") pass: String):Observable<BaseResponse<LogindataModel>>
    @GET("/api/driver/car/index")
    fun getCabList(
        @Header("token") token: String
    ): Observable<BaseResponse<ArrayList<CabListModel>>>

    @POST("/api/driver/car/change-car-status")
    @FormUrlEncoded
    fun changeStatus(
        @Header("token") token: String,
        @Field("car_id") car_id:Int,
        @Field("status") status:Int
    ): Observable<BaseResponse<ArrayList<CabListModel>>>

    @GET("/api/driver/car/car-models")
    fun getCarModels(
        @Header("token") token: String
    ):Observable<BaseResponse<ArrayList<CarTypeModel>>>
    @POST("/api/driver/car/add-car")
    fun addCab(
        @Header("token") token: String,
        @Body builder: RequestBody
    ):Observable<BaseResponse<CabListModel>>
}


