package com.iteach.taxi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iteach.taxi.Utils.SharedPref.LoginPref
import com.iteach.taxi.databinding.ActivityMainBinding
import com.iteach.taxi.fragment.FragmentChangeListener
import com.iteach.taxi.fragment.signup.ui.Fragment_SignUp
import com.iteach.taxi.viewmodel.MyViewModel

class MainActivity : AppCompatActivity(), FragmentChangeListener {
    private var _binding: ActivityMainBinding?=null
    private val binding get() = _binding!!
    lateinit var viewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inits()

        replaceFragment(Fragment_SignUp())

        viewModel.user.observe(this, Observer {
            LoginPref.SaveLogin(it)

            startActivity(Intent(this,TaxiActivity::class.java))
            finish()
        })
    }

    private fun inits() {
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
    }

    override fun replaceFragment(fragment: Fragment?) {

            val fragmenManeger = supportFragmentManager
            val fragmenTransction = fragmenManeger.beginTransaction()
            if (fragment != null) {
                fragmenTransction.replace(R.id.fragmentContainermain,fragment)
            }
            fragmenTransction.commit()
    }


}