package com.iteach.taxi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.iteach.taxi.databinding.ActivityTaxiBinding
import com.iteach.taxi.fragment.FragmentChangeListener
import com.iteach.taxi.fragment.addcab.ui.CabListFragment
import com.iteach.taxi.fragment.mapfragment.ui.MapFragment
import com.iteach.taxi.fragment.orders.ui.OrderFragment

class TaxiActivity : AppCompatActivity(){
    var _binding :ActivityTaxiBinding?=null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTaxiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //replaceFragment(OrderFragment())
      //  replaceFragment(CabListFragment())
    }


}