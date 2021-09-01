package com.iteach.taxi.fragment.register.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iteach.taxi.TaxiActivity
import com.iteach.taxi.Utils.SharedPref.LoginPref
import com.iteach.taxi.databinding.RegisterBinding
import com.iteach.taxi.fragment.login.base.Login_Password
import com.iteach.taxi.viewmodel.MyViewModel
import io.paperdb.Paper

class FragmentRegister(val logginpassword: Login_Password) : Fragment(){
    private var _binding: RegisterBinding?=null
    private val binding get() = _binding!!
    lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegisterBinding.inflate(inflater,container,false)

        inits()

        sendLogin()

        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(),it , Toast.LENGTH_LONG).show()
        })

        viewModel.user.observe(requireActivity(), Observer {
            LoginPref.SaveLogin(it)
            Toast.makeText(requireContext(),it.first_name,Toast.LENGTH_SHORT).show()
        })

        binding.apply {

            pinview.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {

                    if (pinview.text.toString().length == pinview.itemCount)

                        activateButton()
                    else
                        notactivateButton()
                }

            })

            sendButton.setOnClickListener(View.OnClickListener {
                if (sendButton.alpha==1F){

                        startActivity(Intent(requireActivity(),TaxiActivity::class.java))
                        activity?.finish()
                }
            })

        }

        return binding.root
    }

    private fun inits() {

        Paper.init(requireContext())

        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
    }

    private fun sendLogin() {
        viewModel.sendLogin(logginpassword)
    }

    private fun notactivateButton() {
        binding.sendButton.alpha = 0.5F
    }

    private fun activateButton() {
        binding.sendButton.alpha = 1F
    }


}