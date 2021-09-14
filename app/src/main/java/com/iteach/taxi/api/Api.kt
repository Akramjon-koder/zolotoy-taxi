package com.iteach.taxi.api
import com.iteach.taxi.fragment.addcab.base.CabListModel
import com.iteach.taxi.fragment.addcab.base.CarTypeModel
import com.iteach.taxi.fragment.login.base.LoginModel
import com.iteach.taxi.fragment.mapfragment.base.DefinitionModel
import com.iteach.taxi.fragment.mapfragment.base.EndDefinitionModel
import com.iteach.taxi.fragment.mapfragment.base.OrderBeginModel
import com.iteach.taxi.fragment.mapfragment.base.OrderEndModel
import com.iteach.taxi.fragment.register.base.RegisterModel
import com.iteach.taxi.fragment.register.base.SendCode
import com.iteach.taxi.fragment.signup.base.Send_Register_Model
import com.iteach.taxi.model.BaseResponse
import com.iteach.taxi.model.GetTarifModel
import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import retrofit2.http.*
interface Api {

    @POST(Api_urls.LOGIN_URL)
    @FormUrlEncoded
    fun getLogin(
        @Field("username") username: String,
        @Field("password") pass: String):Observable<BaseResponse<LoginModel>>


    @POST(Api_urls.REGISTR_URL)
    fun sendRegistr(
        @Body sendRegisterModel: Send_Register_Model
    ): Observable<BaseResponse<RegisterModel>>

    @POST(Api_urls.RESEND_REGISTR_CODE_URL)
    @FormUrlEncoded
    fun resendRegistrCode(
        @Field("phone") phone: String
    ): Observable<BaseResponse<Unit>>

    @POST(Api_urls.REGISTR_CODE_URL)
    fun sendRegistrCode(
        @Body sendCode: SendCode
    ):Observable<BaseResponse<LoginModel>>
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
    ):Observable<BaseResponse<ArrayList<CabListModel>>>
    @GET("/api/driver/car/car-models")
    fun getCarModels(
        @Header("token") token: String
    ):Observable<BaseResponse<ArrayList<CarTypeModel>>>
    @POST("/api/driver/car/add-car")
    fun addCab(
        @Header("token") token: String,
        @Body builder: RequestBody):Observable<BaseResponse<CabListModel>>
    @POST(Api_urls.DEFINITION_BEGIN_URL)
    fun senddefinition(
        @Header ("token") token: String,
        @Body definitionModel: DefinitionModel):
            Observable<BaseResponse<OrderBeginModel>>
    @POST(Api_urls.DEFINITION_END_URL)
    fun senddefinitionend(
        @Header ("token") token: String,
        @Body definitionModelEnd: EndDefinitionModel
    ): Observable<BaseResponse<OrderEndModel>>
    @POST("/api/driver/order/get-tariff")
    @FormUrlEncoded
    fun getTarifModel(
        @Header("token") token: String,
        @Field("car_id") car_id:Int
    ) :Observable<BaseResponse<GetTarifModel>>
}

