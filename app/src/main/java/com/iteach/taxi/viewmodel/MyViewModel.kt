package com.iteach.taxi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iteach.taxi.fragment.login.base.LoginModel
import com.iteach.taxi.fragment.login.base.Login_Password
import com.iteach.taxi.fragment.login.repository.LoginRepository
import com.iteach.taxi.fragment.register.base.SendCode
import com.iteach.taxi.fragment.register.repository.RegisterRepository
import com.iteach.taxi.fragment.signup.base.Send_Register_Model
import com.iteach.taxi.fragment.signup.repository.SignUpRepository

class MyViewModel :ViewModel() {

    val login_repository = LoginRepository()

    val signUpRepository = SignUpRepository()

    val registr = RegisterRepository()

    val user = MutableLiveData<LoginModel>()

    val sig_up = MutableLiveData<Int>()

    val error = MutableLiveData<String>()

    fun sendLogin(logginpassword: Login_Password){

        login_repository.sendLogin(error,user,logginpassword)
    }

    fun signUp(sendRegisterModel: Send_Register_Model){
        signUpRepository.sendSignUp(error,sig_up,sendRegisterModel)
    }

    fun registrationCode(sendCode: SendCode){
        registr.sendRegistrCode(error, user, sendCode)
    }
}