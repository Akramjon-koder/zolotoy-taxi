package com.iteach.taxi.fragment.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iteach.taxi.databinding.FragmentLoginBinding
import com.iteach.taxi.fragment.FragmentChangeListenerLogin
import com.iteach.taxi.fragment.login.base.Login_Password
import com.iteach.taxi.fragment.register.ui.FragmentRegister

class LoginFragment : Fragment(){
    private var _binding: FragmentLoginBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)

        binding.buttonlogin.setOnClickListener(View.OnClickListener {

            val phone = "+998936448111"   //binding.editText.text.toString()
            val password ="12345678"  //Integer.parseInt(binding.editText2.text.toString())


            replaceFragment(FragmentRegister(Login_Password(phone,password)))
        })
        return binding.root
    }
    private fun replaceFragment(fragment: Fragment) {
        val fc = activity as FragmentChangeListenerLogin?
        fc!!.replaceFragment(fragment,"+998336694838")

    }
}