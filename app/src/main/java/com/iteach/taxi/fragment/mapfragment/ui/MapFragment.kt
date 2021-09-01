package com.iteach.taxi.fragment.mapfragment.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
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
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.iteach.taxi.databinding.FragmentMapBinding
import java.util.jar.Manifest


class MapFragment: Fragment(),OnMapReadyCallback{

    private var _binding: FragmentMapBinding?=null
    private val binding get() = _binding!!
    private lateinit var googleMap :GoogleMap
    private val REQUES_CALL =1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    }

    private fun getDirectionsUrl(origin: LatLng, dest: LatLng): String? {

        // Origin of route
        val str_origin = "origin=" + origin.latitude + "," + origin.longitude

        // Destination of route
        val str_dest = "destination=" + dest.latitude + "," + dest.longitude

        // Sensor enabled
        val sensor = "sensor=false"
        val mode = "mode=driving"

        // Building the parameters to the web service
        val parameters = "$str_origin&$str_dest&$sensor&$mode"

        // Output format
        val output = "json"

        // Building the url to the web service
        return "https://maps.googleapis.com/maps/api/directions/$output?$parameters"
    }

//    private class DownloadTask() : AsyncTaskLoader, Parcelable {
//        constructor(parcel: Parcel) : this() {
//        }
//
//        override fun writeToParcel(parcel: Parcel, flags: Int) {
//
//        }
//
//        override fun describeContents(): Int {
//            return 0
//        }
//
//        companion object CREATOR : Parcelable.Creator<DownloadTask> {
//            override fun createFromParcel(parcel: Parcel): DownloadTask {
//                return DownloadTask(parcel)
//            }
//
//            override fun newArray(size: Int): Array<DownloadTask?> {
//                return arrayOfNulls(size)
//            }
//        }
//
//    }

}