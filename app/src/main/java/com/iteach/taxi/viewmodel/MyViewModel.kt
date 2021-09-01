package com.iteach.taxi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iteach.taxi.fragment.login.base.Login_Password
import com.iteach.taxi.fragment.login.repository.LoginRepository
import com.iteach.taxi.model.LogindataModel

class MyViewModel :ViewModel() {

    val login_repository = LoginRepository()

    val user = MutableLiveData<LogindataModel>()

    val error = MutableLiveData<String>()

    fun sendLogin(logginpassword: Login_Password){

        login_repository.sendLogin(error,user,logginpassword)
    }
}