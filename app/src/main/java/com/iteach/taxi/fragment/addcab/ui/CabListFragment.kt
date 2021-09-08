package com.iteach.taxi.fragment.addcab.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.esotericsoftware.minlog.Log
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.iteach.taxi.R
import com.iteach.taxi.databinding.FragmentCabListBinding
import com.iteach.taxi.fragment.addcab.base.CabListModel
import com.iteach.taxi.fragment.addcab.base.CabListVM
import com.iteach.taxi.fragment.addcab.ui.adapter.CabListAdapter

class CabListFragment : Fragment() {
    private var _binding: FragmentCabListBinding? = null
    val binding get() = _binding!!
    lateinit var viewmodel: CabListVM
    lateinit var adapter: CabListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCabListBinding.inflate(inflater, container, false)
        addCab()
        initUI()
        initVM()
        getCabList()
        succes()
        funerror()
        return binding.root
    }
    private fun initUI(){
          binding.apply {
              adapter = CabListAdapter(requireContext(), arrayListOf(),object :CabListAdapter.OnItemClickListener{
                  override fun ClickListener(model: CabListModel) {
                        changeActive(model)
                  }
              })
              recyclerview.setHasFixedSize(true)
              recyclerview.layoutManager =LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
              recyclerview.adapter =adapter
          }
    }
    private fun changeActive(model: CabListModel) {
        var builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Bugun "+model.model?.name+" avtomobilni minasizmi?")
            .setPositiveButton("Ha") { dialog, which->
                viewmodel.changeStatus("mwveJwYMbhVkJbAj_1630994254", model.id?.toInt()!!,1)
                viewmodel.changeLiveData.observe(viewLifecycleOwner,{
                  adapter.setData(it)
                })
            }
            .setNegativeButton("Qaytish"){dialog, which->}
            .show()
    }

    private fun funerror() {
        viewmodel.error.observe(viewLifecycleOwner, {
            Log.error("xatolik", it.toString())
        })
    }
    private fun addCab() {
        binding.addCab.setOnClickListener {
            findNavController().navigate(R.id.action_cabListFragment_to_addCabFragment)
        }
    }

    private fun succes() {
        viewmodel.user.observe(viewLifecycleOwner, {
            Log.error("asadsa", it.toString())
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
        viewmodel.getCabList("mwveJwYMbhVkJbAj_1630994254")
    }

}