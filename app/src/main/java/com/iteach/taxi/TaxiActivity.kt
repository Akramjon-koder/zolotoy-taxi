package com.iteach.taxi

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.iteach.taxi.databinding.ActivityTaxiBinding
import com.iteach.taxi.fragment.FragmentChangeListener
import com.iteach.taxi.fragment.mapfragment.ui.MapFragment
import com.iteach.taxi.fragment.orders.ui.OrderFragment

class TaxiActivity : AppCompatActivity(),FragmentChangeListener {
    var _binding :ActivityTaxiBinding?=null
    val binding get() = _binding!!
    private val LOCATION_REQUEST_CODE =10001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTaxiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(OrderFragment())

        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION)){

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_REQUEST_CODE
                )
            }
        }
    }

    override fun replaceFragment(fragment: Fragment?) {
        val fragmenManeger = supportFragmentManager
        val fragmenTransction = fragmenManeger.beginTransaction()
        if (fragment != null) {
            fragmenTransction.replace(R.id.fragmentContainer,fragment)
        }
        fragmenTransction.commit()
    }
}