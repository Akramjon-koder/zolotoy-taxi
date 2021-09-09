package com.iteach.taxi.fragment.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iteach.taxi.databinding.FragmentLoginBinding
import com.iteach.taxi.fragment.login.base.Login_Password
import com.iteach.taxi.viewmodel.MyViewModel

class LoginFragment : Fragment(){
    private var _binding: FragmentLoginBinding?=null
    private val binding get() = _binding!!
    lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        binding.buttonlogin.setOnClickListener(View.OnClickListener {

            viewModel.sendLogin(Login_Password(binding.editText.text.toString(),binding.editText2.text.toString()))

        })
        return binding.root
    }
}