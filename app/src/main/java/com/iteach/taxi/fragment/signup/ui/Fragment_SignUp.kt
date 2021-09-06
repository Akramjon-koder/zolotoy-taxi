package com.iteach.taxi.fragment.signup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.iteach.taxi.databinding.FragmentSignupBinding
import com.iteach.taxi.fragment.FragmentChangeListener
import com.iteach.taxi.fragment.login.ui.LoginFragment
import com.iteach.taxi.fragment.register.ui.FragmentRegister
import com.iteach.taxi.fragment.signup.base.Send_Register_Model

class Fragment_SignUp :Fragment(){
    private var _binding: FragmentSignupBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater,container,false)
        binding.apply {

            buttonlogin.setOnClickListener(View.OnClickListener {
                replaceFragment(LoginFragment())
            })

            buttonregistr.setOnClickListener(View.OnClickListener {
                val sendRegisterModel = Send_Register_Model(phone.text.toString(),name.text.toString(),surname.text.toString(),password.text.toString())
                replaceFragment(FragmentRegister(sendRegisterModel))
            })

        }

        return binding.root
    }
    private fun replaceFragment(fragment: Fragment) {
        val fc = activity as FragmentChangeListener?
        fc!!.replaceFragment(fragment,)

    }

}