package com.iteach.taxi.fragment.mapfragment.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.iteach.taxi.databinding.FragmentMapBinding
import com.mindorks.ridesharing.utils.PermissionUtils


class MapFragment: Fragment(),OnMapReadyCallback{

    private var _binding: FragmentMapBinding?=null
    private val binding get() = _binding!!
    private lateinit var googleMap :GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val REQUES_CALL =1
    private val LOCATION_PERMISSION_REQUEST_CODE = 999
    private var mHandler: Handler? = null
    private var dis: Double = 0.0
    private var begin: LatLng? = null
    private var end: LatLng? = null
    private var start0rstop = true
private var fusedLocationProviderClient: FusedLocationProviderClient?=null
    private lateinit var locationCallback: LocationCallback
    private  var currentLatLng:LatLng?=null



    lateinit var locationRequest: LocationRequest
    lateinit var locationcallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHandler = Handler()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        locationRequest = LocationRequest.create().apply {
            interval = 5000
            fastestInterval = 5000
            priority =LocationRequest.PRIORITY_HIGH_ACCURACY

        }
        locationcallback = object: LocationCallback(){

            override fun onLocationResult(locationresult: LocationResult) {
                super.onLocationResult(locationresult)
                locationresult ?: return
                for (location in locationresult.locations){
                    if (begin==null){
                        begin =LatLng(location.latitude,location.longitude)
                    }else{
                        dis = dis +distanse(begin!!.latitude,begin!!.longitude,location.latitude,location.longitude)

                        Toast.makeText(requireContext(),"dis: "+dis+" metr",Toast.LENGTH_SHORT).show()
                    }
                    Toast.makeText(requireContext(),"latlon: "+location.latitude+"\n"+location.longitude,Toast.LENGTH_SHORT).show()
                }

            }

        }


    }

    private fun calcilateDstanse() {
        setUpLocationListener()

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 20f))
        if (begin==null){
            begin = currentLatLng
        }else{

            val loc =currentLatLng

            if (loc != null && begin !=null) {
                dis = dis + distanse(loc.latitude,loc.longitude, begin!!.latitude, begin!!.longitude)
            }

            Toast.makeText(requireContext(),"dis: "+dis.toString(),Toast.LENGTH_SHORT).show()
            Toast.makeText(requireContext(),"latlon: "+currentLatLng.toString(),Toast.LENGTH_SHORT).show()
        }


    }

    private fun distanse(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) +
                (Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                        * Math.cos(deg2rad(theta)))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist =
            dist * 60 // 60 nautical miles  per degree of seperation  //Seperatsiya darajasiga 60 dengiz mil
        dist = dist * 1852 // 1820 meters per nautial mile  // Har bir dengiz mili 1820 metr
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(deg: Double): Double {
        return deg * 180.0 / Math.PI
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
            startButton.setOnClickListener(View.OnClickListener {
                if (start0rstop){
//                    dis = 0.0
//                    startRepeatingTask()
                    start0rstop = false
                   startButton.setText("Hisobni to'xtatish")

                    start()

                }else{
//                    stopRepeatingTask()
                   start0rstop = true
                   startButton.setText("Hisobni boshlash")

                    stop()
                }

            })

            getLocationnn.setOnClickListener(View.OnClickListener {

                val distanse = distanse(40.373880748356946, 71.80497186762656,40.42198965122155, 71.77347200532392)
                Toast.makeText(requireContext(),distanse.toString(),Toast.LENGTH_SHORT).show()
            })
        }

        return binding.root
    }
    private fun start(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,locationcallback, Looper.getMainLooper())
    }

    private fun stop(){
        fusedLocationClient.removeLocationUpdates(locationcallback)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    when {
                        PermissionUtils.isLocationEnabled(requireContext()) -> {
                            setUpLocationListener()
                        }
                        else -> {
                            PermissionUtils.showGPSNotEnabledDialog(requireContext())
                        }
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "ruxsat berilmagan",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        if (currentLatLng == null) {
            when {
                PermissionUtils.isAccessFineLocationGranted(requireContext()) -> {
                    when {
                        PermissionUtils.isLocationEnabled(requireContext()) -> {
                            setUpLocationListener()
                        }
                        else -> {
                            PermissionUtils.showGPSNotEnabledDialog(requireContext())
                        }
                    }
                }
                else -> {
                    PermissionUtils.requestAccessFineLocationPermission(
                        requireActivity(),
                        LOCATION_PERMISSION_REQUEST_CODE
                    )
                }
            }
        }
    }
    private fun setUpLocationListener() {
        fusedLocationProviderClient = FusedLocationProviderClient(requireActivity())
        // for getting the current location update after every 2 seconds
        val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                if (currentLatLng == null) {
                    for (location in locationResult.locations) {
                        if (currentLatLng == null) {
                            currentLatLng = LatLng(location.latitude, location.longitude)


                         //     setCurrentLocationAsPickUp()
//                            enableMyLocationOnMap()
//                            moveCamera(currentLatLng)
//                            animateCamera(currentLatLng)
//                            presenter.requestNearbyCabs(currentLatLng!!)
                        }
                    }
                }
                // Few more things we can do here:
                // For example: Update the location of user on server
            }
        }
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
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




}


