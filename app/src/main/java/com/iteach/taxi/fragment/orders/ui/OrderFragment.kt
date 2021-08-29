package com.iteach.taxi.fragment.orders.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.iteach.taxi.databinding.FragmentOrderBinding
import com.iteach.taxi.fragment.FragmentChangeListener
import com.iteach.taxi.fragment.mapfragment.ui.MapFragment
import com.iteach.taxi.fragment.orders.base.OrderModel


class OrderFragment : Fragment(),AdapterRecyclerOrder.OnItemClickListner{
    private var _binding:FragmentOrderBinding?=null
     private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      _binding = FragmentOrderBinding.inflate(inflater,container,false)

        startrecyclerview()


      return binding.root
    }

    private fun startrecyclerview() {
        binding.apply {
            var list = arrayListOf<OrderModel>()
            list.add(OrderModel("farrux","Ул. Воскова, 4","nПарковая, 21Б"))
            list.add(OrderModel("farrux","Ул. Воскова, 4","nПарковая, 21Б"))
            list.add(OrderModel("farrux","Ул. Воскова, 4","nПарковая, 21Б"))
            list.add(OrderModel("farrux","Ул. Воскова, 4","nПарковая, 21Б"))
            list.add(OrderModel("farrux","Ул. Воскова, 4","nПарковая, 21Б"))
            list.add(OrderModel("farrux","Ул. Воскова, 4","nПарковая, 21Б"))
            list.add(OrderModel("farrux","Ул. Воскова, 4","nПарковая, 21Б"))
            list.add(OrderModel("farrux","Ул. Воскова, 4","nПарковая, 21Б"))
            list.add(OrderModel("farrux","Ул. Воскова, 4","nПарковая, 21Б"))
            list.add(OrderModel("farrux","Ул. Воскова, 4","nПарковая, 21Б"))
            list.add(OrderModel("farrux","Ул. Воскова, 4","nПарковая, 21Б"))
            list.add(OrderModel("farrux","Ул. Воскова, 4","nПарковая, 21Б"))
            list.add(OrderModel("farrux","Ул. Воскова, 4","nПарковая, 21Б"))
            list.add(OrderModel("farrux","Ул. Воскова, 4","nПарковая, 21Б"))
            recyclerOrders.setHasFixedSize(true)
            recyclerOrders.layoutManager = GridLayoutManager(requireContext(),3,GridLayoutManager.VERTICAL,false)
            recyclerOrders.adapter = AdapterRecyclerOrder(list,this@OrderFragment)

        }
    }

    override fun orderClicked(order: OrderModel) {
        val alerDialog = MaterialAlertDialogBuilder(requireContext())
        alerDialog.setTitle(order.userName+"\n"+order.where+"\n"+order.wherefrom)
        alerDialog.setPositiveButton("ha"){dialog,which->

            replaceFragment(MapFragment())

        }
        alerDialog.setNegativeButton("yo'q"){dialod,which->
        }

        alerDialog.show()

    }

    private fun replaceFragment(fragment: Fragment) {

        val fc = activity as FragmentChangeListener?
        fc!!.replaceFragment(fragment)


    }


}