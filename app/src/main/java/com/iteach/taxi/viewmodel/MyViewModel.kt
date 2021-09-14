package com.iteach.taxi.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iteach.taxi.fragment.login.base.LoginModel
import com.iteach.taxi.fragment.login.base.Login_Password
import com.iteach.taxi.fragment.login.repository.LoginRepository
import com.iteach.taxi.fragment.mapfragment.base.DefinitionModel
import com.iteach.taxi.fragment.mapfragment.base.EndDefinitionModel
import com.iteach.taxi.fragment.mapfragment.base.OrderBeginModel
import com.iteach.taxi.fragment.mapfragment.base.OrderEndModel
import com.iteach.taxi.fragment.mapfragment.repository.SendOrderBegin
import com.iteach.taxi.fragment.mapfragment.repository.SendOrderEnd
import com.iteach.taxi.fragment.register.base.RegisterModel
import com.iteach.taxi.fragment.register.base.SendCode
import com.iteach.taxi.fragment.register.repository.RegisterRepository
import com.iteach.taxi.fragment.register.repository.ResendSMS
import com.iteach.taxi.fragment.signup.base.Send_Register_Model
import com.iteach.taxi.fragment.signup.repository.SignUpRepository
import com.iteach.taxi.model.BaseResponse

class MyViewModel :ViewModel() {
    val login_repository = LoginRepository()
    val signUpRepository = SignUpRepository()
    val registr = RegisterRepository()

    val resendRepository = ResendSMS()
    val SMS =MutableLiveData<Boolean>()

    val user = MutableLiveData<LoginModel>()
    val sig_up = MutableLiveData<RegisterModel>()
    val error = MutableLiveData<String>()
    val error1 = MutableLiveData<String>()
    fun sendLogin(logginpassword: Login_Password){
        login_repository.sendLogin(error,user,logginpassword)
    }
    fun signUp(sendRegisterModel: Send_Register_Model){
        signUpRepository.sendSignUp(error1,sig_up,sendRegisterModel)
    }
    fun registrationCode(sendCode: SendCode){
        registr.sendRegistrCode(error, user, sendCode)
    }

    fun ResendSMSCode(phone: String){
        resendRepository.resendSMS(error, SMS, phone)
    }


    val sendOrderBegin = SendOrderBegin()

    val orderBegin =MutableLiveData<OrderBeginModel>()

    fun orderBegining(token:String,definitionModel: DefinitionModel){
        sendOrderBegin.sendOrderStart(error,orderBegin,definitionModel,token)
    }
    val sendOrderEnd = SendOrderEnd()
    val orderEnd =MutableLiveData<OrderEndModel>()
    fun orderEnded(token:String,definitionModelEnd: EndDefinitionModel){
        sendOrderEnd.sendOrderStart(error,orderEnd,definitionModelEnd,token)
    }
}