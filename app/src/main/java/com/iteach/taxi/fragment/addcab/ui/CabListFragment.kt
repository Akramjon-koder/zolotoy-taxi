package com.iteach.taxi.fragment.addcab.ui
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.iteach.taxi.R
import com.iteach.taxi.Utils.Constants
import com.iteach.taxi.Utils.SharedPref.ActiveCabSaveId
import com.iteach.taxi.Utils.alert
import com.iteach.taxi.databinding.FragmentCabListBinding
import com.iteach.taxi.fragment.addcab.base.CabListModel
import com.iteach.taxi.fragment.addcab.base.CabListVM
import com.iteach.taxi.fragment.addcab.ui.adapter.CabListAdapter
import com.iteach.taxi.fragment.orders.ui.OrderFragment
class CabListFragment : Fragment() {
    private var _binding: FragmentCabListBinding? = null
    val binding get() = _binding!!
    lateinit var viewmodel: CabListVM
    lateinit var adapter: CabListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View? {
        _binding = FragmentCabListBinding.inflate(inflater, container, false)
        binding.SpinkitView.visibility = View.VISIBLE
        OrderFragment.intentCablist =1
        onBackPressed()
        addCab()
        initUI()
        initVM()
        getCabList()
        succes()
        funerror()
        return binding.root
    }
    private fun onBackPressed() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(requireActivity(), object:
                OnBackPressedCallback(true) {
                override fun handleOnBackPressed(){
                    if(ActiveCabSaveId.getId()==0){
                        Toast.makeText(requireContext(), "Sizga Admin tomonidan Xabar beriladi", Toast.LENGTH_SHORT).show()
                        alert("Avtomobilingiz ko'rib chiqiladi va sizga admin tomonidan xabar beriladi", "") {
                            positiveButton("Roziman") {
                                requireActivity().finish()
                            }
                            negativeButton("Cancel"){
                            }
                        }.show()
                    }else{
                        findNavController().popBackStack()
                    }
                }
            })
    }

    private fun initUI() {
        binding.apply {
            adapter = CabListAdapter(
                requireContext(),
                arrayListOf(),
                object : CabListAdapter.OnItemClickListener {
                    override fun ClickListener(model: CabListModel) {
                        changeActive(model)
                    }
                })
            recyclerview.setHasFixedSize(true)
            recyclerview.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerview.adapter = adapter
        }
    }

    private fun changeActive(model: CabListModel) {
        if (model.status == 2) {
            Toast.makeText(
                requireContext(),
                "Mashinagiz admin tomonidan aktiv qilinmagan ",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        var builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Bugun " + model.model?.name + " avtomobilni minasizmi?")
            .setPositiveButton("Ha") { dialog, which ->
                binding.SpinkitView.visibility = View.VISIBLE
                viewmodel.changeStatus(Constants.token, model.id?.toInt()!!, 1)
                viewmodel.changeLiveData.observe(viewLifecycleOwner, {
                    adapter.setData(it)
                    ActiveCabSaveId.saveId(model.id)
                    //Log.error("cabId",ActiveCabSaveId.getId().toString())
                    binding.SpinkitView.visibility = View.GONE
                })
            }
            .setNegativeButton("Qaytish") { dialog, which -> }
            .show()
    }

    private fun funerror() {
        viewmodel.error.observe(viewLifecycleOwner, {
            Log.e("xatolik", it.toString())
            binding.SpinkitView.visibility = View.GONE
        })
    }

    private fun addCab() {
        binding.addCab.setOnClickListener {
            findNavController().navigate(R.id.action_cabListFragment_to_addCabFragment)
        }
    }

    private fun succes() {
        viewmodel.user.observe(viewLifecycleOwner, {
            binding.SpinkitView.visibility = View.GONE
            adapter.setData(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initVM() {
        viewmodel = ViewModelProvider(requireActivity()).get(CabListVM::class.java)
    }

    private fun getCabList() {
        viewmodel.getCabList(Constants.token)
    }


}