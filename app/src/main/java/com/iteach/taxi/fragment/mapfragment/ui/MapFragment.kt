package com.iteach.taxi.fragment.mapfragment.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.loader.content.AsyncTaskLoader
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnSuccessListener
import com.iteach.taxi.databinding.FragmentMapBinding
import java.util.jar.Manifest


class MapFragment: Fragment(),OnMapReadyCallback{

    private var _binding: FragmentMapBinding?=null
    private val binding get() = _binding!!
    private lateinit var googleMap :GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val REQUES_CALL =1
    private val LOCATION_REQUEST_CODE =10001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater,container,false)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()
        binding.mapView.getMapAsync(this)

        binding.apply {
            phone.setOnClickListener(View.OnClickListener {
                makePhoneCall()
            })
        }

        return binding.root
    }

    private fun getLastLocation(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->}
        }else{
            askLocationPermission()
        }

    }

    private fun askLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION)){

                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_REQUEST_CODE
                )
            }
        }
    }

    private fun makePhoneCall() {

        val phone_number = "+998336694838"
        if (phone_number.trim().length>0){

            if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    REQUES_CALL
                )
            }else{
                val dial = "tel:" +phone_number
                startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
            }

        }

    }


    override fun onMapReady(map: GoogleMap) {

        map.let {
            googleMap = it
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUES_CALL){
            makePhoneCall()
            if (grantResults.size > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

            }else Toast.makeText(requireContext(),"Iltimos ruxsat bering.",Toast.LENGTH_SHORT).show()
        }

        if (requestCode == LOCATION_REQUEST_CODE){
            if (grantResults.size > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getLastLocation()
            }else Toast.makeText(requireContext(),"Iltimos ruxsat bering.",Toast.LENGTH_SHORT).show()
        }
    }


}