package com.iteach.taxi.fragment.mapfragment.ui

import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.content.AsyncTaskLoader
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.iteach.taxi.databinding.FragmentMapBinding


class MapFragment: Fragment(),OnMapReadyCallback{

    private var _binding: FragmentMapBinding?=null
    private val binding get() = _binding!!
    private lateinit var googleMap :GoogleMap

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

        return binding.root
    }

    override fun onMapReady(map: GoogleMap) {

        map.let {
            googleMap = it
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