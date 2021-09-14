package com.iteach.taxi.fragment.signup.ui
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.iteach.taxi.R
import com.iteach.taxi.databinding.FragmentSignupBinding
import com.iteach.taxi.fragment.FragmentChangeListener
import com.iteach.taxi.fragment.login.ui.LoginFragment
import com.iteach.taxi.fragment.register.ui.FragmentRegister
import com.iteach.taxi.fragment.signup.base.Send_Register_Model
import com.iteach.taxi.viewmodel.MyViewModel
class Fragment_SignUp : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    lateinit var sendRegisterModel: Send_Register_Model
    lateinit var viewModel: MyViewModel
    var intent: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        intent = 0
        viewModel.error1.observe(requireActivity(), Observer {
            Toast.makeText(
                requireContext(),
                "Bu nomerdan avval Ro'yxatdan o'tilgan",
                Toast.LENGTH_SHORT
            ).show()
        })

        binding.apply {
            buttonlogin.setOnClickListener(View.OnClickListener {
            })
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonregistr.setOnClickListener(View.OnClickListener {
                sendRegisterModel = Send_Register_Model(
                    phone.text.toString(),
                    name.text.toString(),
                    surname.text.toString(),
                    password.text.toString())
                ViewModelProvider(requireActivity())[SendRegisterVM::class.java].setData(
                    sendRegisterModel
                )
                if (Tekshirish()) {
                    signUp()
                    viewModel.sig_up.observe(viewLifecycleOwner, Observer {
                        Log.e("ashdaugf", it.toString())
                        Navigation.findNavController(requireView()).navigate(R.id.action_fragment_SignUp_to_confirmCodeFragment)
                    })

                }
                /*  val sendRegisterModel = Send_Register_Model(phone.text.toString(),name.text.toString(),surname.text.toString(),password.text.toString())
                  replaceFragment(com.iteach.taxi.fragment.register.ui.FragmentRegister(sendRegisterModel))*/
            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    private fun Tekshirish(): Boolean {
        if (binding.name.text.length < 3) {
            Toast.makeText(requireContext(), "iltimos ismingizni to'liq yozing", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (binding.surname.text.length < 3) {
            Toast.makeText(
                requireContext(),
                "iltimos familiyaningizni to'liq yozing",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        if (binding.phone.text.length == 11) {
            Toast.makeText(
                requireContext(),
                "iltimos telefon nomeringizni to'liq yozing",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        if (binding.password.text.length < 3) {
            Toast.makeText(requireContext(), "iltimos uzunroq parol yozing", Toast.LENGTH_SHORT)
                .show()
            return false
        }else {
            return true
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun signUp() {
        viewModel.signUp(sendRegisterModel)
    }
}

class SendRegisterVM : ViewModel() {
    private var _data = MutableLiveData<Send_Register_Model>()
    val data: LiveData<Send_Register_Model> get() = _data
    fun setData(model: Send_Register_Model) {
        _data.value = model
    }
}