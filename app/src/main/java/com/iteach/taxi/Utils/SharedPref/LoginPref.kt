package com.iteach.taxi.Utils.SharedPref

import com.iteach.taxi.fragment.login.base.LoginModel
import io.paperdb.Paper

object LoginPref {
    private const val  login ="fdjnbds"
     fun SaveLogin(model: LoginModel){
         Paper.book().write(login,model)
     }

}