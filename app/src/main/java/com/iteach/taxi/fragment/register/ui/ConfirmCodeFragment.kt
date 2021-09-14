package com.iteach.taxi.fragment.register.ui
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iteach.taxi.TaxiActivity
import com.iteach.taxi.Utils.SharedPref.LoginPref
import com.iteach.taxi.databinding.FragmentConfirmCodeBinding
import com.iteach.taxi.fragment.register.base.SendCode
import com.iteach.taxi.fragment.signup.base.Send_Register_Model
import com.iteach.taxi.fragment.signup.ui.Fragment_SignUp
import com.iteach.taxi.fragment.signup.ui.SendRegisterVM
import com.iteach.taxi.viewmodel.MyViewModel
class ConfirmCodeFragment : Fragment() {
    private var _binding: FragmentConfirmCodeBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: MyViewModel
    var resend = false
    private lateinit var sendRegisterModel: Send_Register_Model
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConfirmCodeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        ViewModelProvider(requireActivity())[SendRegisterVM::class.java].data.observe(viewLifecycleOwner, {
            sendRegisterModel = it
                signUp()
            })
        binding.apply {
            viewModel.SMS.observe(requireActivity(), Observer {
                resend = false
                var time = 120
                val timer = object : CountDownTimer(120000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        time -= 1
                        TextViewTimer.setText("$time s qoldi") }
                    override fun onFinish() {
                        resend = true
                        TextViewTimer.setText("Iltimos qayta yuboring")
                        sendButton.setText("SMS ni qayta yuborish")
                        binding.sendButton.alpha = 1F
                    }
                }
                timer.start()
            })
          viewModel.error.observe(viewLifecycleOwner,{
              Toast.makeText(requireContext(), "Kod xato kiritildi", Toast.LENGTH_SHORT).show()
          })
            viewModel.user.observe(requireActivity(), Observer {
                LoginPref.SaveLogin(it)
                startActivity(Intent(requireContext(), TaxiActivity::class.java))
                activity?.finish()
            })
            viewModel.sig_up.observe(requireActivity(), Observer {
                pinview.setFocusable(true)
                pinview.setFocusableInTouchMode(true)

                var time = 120
                val timer = object : CountDownTimer(120000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        time -= 1
                        TextViewTimer.setText("$time s qoldi")
                    }

                    override fun onFinish() {
                        resend = true
                        TextViewTimer.setText("Iltimos qayta yuboring")
                        sendButton.setText("SMS ni qayta yuborish")
                        binding.sendButton.alpha = 1F
                    }
                }
                timer.start()
            })

            pinview.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (pinview.text.toString().length == pinview.itemCount)

                        activateButton()
                    else
                        notactivateButton()
                }
            })
            sendButton.setOnClickListener(View.OnClickListener {
                if (sendButton.alpha == 1F) {

                    if (resend) {

                        resendSMSSend()
                    } else {
                        registr()
                    }
                    binding.sendButton.alpha = 0.5F
                    binding.sendButton.setText("Yuborish")

                }

            })

        }

        return binding.root
    }

    private fun resendSMSSend() {
           viewModel.ResendSMSCode(sendRegisterModel.phone.toString())
    }

    private fun registr() {
             viewModel.registrationCode(
                 SendCode(sendRegisterModel.phone, binding.pinview.text.toString()))
    }
    private fun signUp() {
        viewModel.signUp(sendRegisterModel)
    }
    private fun sendLogin() {
        //viewModel.sendLogin(logginpassword)
    }

    private fun notactivateButton() {
        binding.sendButton.alpha = 0.5F
    }

    private fun activateButton() {
        binding.sendButton.alpha = 1F
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModelStore.clear()
    }
}