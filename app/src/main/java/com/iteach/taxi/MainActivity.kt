package com.iteach.taxi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.iteach.taxi.fragment.FragmentChangeListener

import com.iteach.taxi.fragment.login.ui.LoginFragment
import com.iteach.taxi.fragment.orders.ui.OrderFragment
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //  startFragment(Fragment_SignUp())


    }

  /*  override fun replaceFragment(fragment: Fragment?, phone: String) {
        if ( !phone.equals("") ){
            val fragmenManeger = supportFragmentManager
            val fragmenTransction = fragmenManeger.beginTransaction()
            if (fragment != null) {
                fragmenTransction.replace(R.id.fragmentContainermain,fragment)
            }
            fragmenTransction.commit()
        }
    }*/

}