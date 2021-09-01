package com.iteach.taxi.Utils.SharedPref

import com.iteach.taxi.fragment.login.base.LoginModel
import com.iteach.taxi.model.LogindataModel
import io.paperdb.Paper

object LoginPref {
    private const val  login ="fdjnbds"
     fun SaveLogin(model: LogindataModel){
         Paper.book().write(login,model)
     }

}