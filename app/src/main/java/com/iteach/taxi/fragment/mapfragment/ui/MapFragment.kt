package com.iteach.taxi.fragment.mapfragment.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.gms.location.*

import com.iteach.taxi.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.JsonObject
import com.iteach.taxi.Utils.*
import com.iteach.taxi.Utils.SharedPref.TarifSave
import com.iteach.taxi.databinding.FragmentMapBinding
import com.iteach.taxi.fragment.mapfragment.base.OrderBeginModel
import com.iteach.taxi.fragment.orders.ui.OrderIdShareVM
import com.iteach.taxi.viewmodel.MyViewModel
import com.mindorks.ridesharing.utils.PermissionUtils
import info.texnoman.texnomart.utils.CheckInternet.ConnectivityListener
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import org.json.JSONException
import uz.algorithmgateway.project.ScoketViewModel
import com.iteach.taxi.Socket.SocketErrorVM
import com.iteach.taxi.Socket.SocketListener

class MapFragment : Fragment(), OnMapReadyCallback, ConnectivityListener {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val REQUES_CALL = 1
    private val LOCATION_PERMISSION_REQUEST_CODE = 999
    private var dis: Double = 0.0
    private var begin: LatLng? = null
    private var start0rstop = true
    private var order_id = 0
    private var trynumber:Int =0
    private var phoneNumber: String = ""
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var locationCallback: LocationCallback
    private var currentLatLng: LatLng? = null
    lateinit var viewModel: MyViewModel
    private lateinit var orderBeginModel: OrderBeginModel
    lateinit var locationRequest: LocationRequest
    lateinit var locationcallback: LocationCallback
    private var webSocket: WebSocket? = null
    lateinit var latlongList: ArrayList<LatLng>
    private var destinationMarker: Marker? = null
    private var originMarker: Marker? = null
    private var greyPolyLine: Polyline? = null
    private var blackPolyline: Polyline? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        latlongList = ArrayList()
        Places.initialize(requireContext(), getString(R.string.google_maps_key));
        getOrderId()
        fusedLocation()
    }

    private fun fusedLocation() {
        // vT45Gn6Tjn
        Thread {
            Thread.sleep(1000)
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            locationRequest = LocationRequest.create().apply {
                interval = 7000
                fastestInterval = 7000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            locationcallback = object : LocationCallback() {
                override fun onLocationResult(locationresult: LocationResult) {
                    super.onLocationResult(locationresult)
                    locationresult ?: return
                    for (location in locationresult.locations) {
                        if (begin == null) {
                            begin = LatLng(location.latitude, location.longitude)
                            Log.e(
                                "beginlocation",
                                begin!!.longitude.toString() + " " + begin!!.latitude.toString()
                            )
                            googleMap.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(begin, 16f),
                                3000,
                                null
                            )
                        } else {
                            val distanselatlon = distanse(begin!!.latitude, begin!!.longitude, location.latitude, location.longitude)
                            latlongList.add(LatLng(location.latitude, location.longitude))
                            showPath(latlongList)
                            currentLatLng = LatLng(location.latitude, location.longitude)
                            start0rstop = false
                            if (distanselatlon > 1) {
                                dis = dis + distanselatlon
                                begin = LatLng(location.latitude, location.longitude)
                            }
                            googleMap.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(begin, 16f), 4000, null
                            )
                            binding.km.text = ((Math.ceil(dis)) / 1000).toString() + "km"
                            // Toast.makeText(requireContext(), "dis: " + dis + " metr", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }.start()
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
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        socketVM()
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()
        binding.mapView.getMapAsync(this)
        instantiateWebSocket()
        CheckSocketConnect()
        binding.apply {
            phone.setOnClickListener(View.OnClickListener {
                makePhoneCall()
            })
            startButton.setOnClickListener(View.OnClickListener {
                Log.e("hisobnitoxtatish", start0rstop.toString())
                if (start0rstop) {
                    try {

                        if (!requireContext().checkConnection()) {
                            alertDialog("Internetingizni tekshiring")
                        } else {
                            beginOrder(currentLatLng?.latitude!!, currentLatLng?.longitude!!)
                            dis = 0.0
                            Timer()
                            this@MapFragment.begin = null
                            start()
                            linearLayout4.background =
                                getDrawable(requireContext(), R.drawable.outlinebackground)
                            startButton.setTextColor(getColor(requireContext(), R.color.text_color))
                            startButton.setText("Hisobni to'xtatish")
                            start0rstop = false
                        }
                    } catch (e: Exception) {
                        Log.e("xatolik", e.toString())
                    }
                } else {
                 //   Log.e("toxtadi", "dmwfe")
                    var builder = MaterialAlertDialogBuilder(requireContext())
                    builder.setTitle("Manzilga yetib keldingizmi ?")
                    builder.setPositiveButton("Ha") { dialog, when1 ->
                        if (!requireContext().checkConnection()) {
                            alertDialog("Internetingizni tekshiring") } else {
                            endTaxi()
                        }
                    }
                    builder.setNegativeButton("Yo'q") { dialog, when1 ->
                        dialog.dismiss()
                    }
                    builder.show()

                }
            })
        }
        return binding.root
    }

    private fun endTaxi() {
        stop()
        var total_amount: Int
        if (dis / 1000 < TarifSave.getTarif().minimumKm!!) {
            total_amount = TarifSave.getTarif().minimumSum!!
        } else {
            total_amount =
                (TarifSave.getTarif().minimumSum!! + TarifSave.getTarif().minimumKm!! * dis / 1000).toInt()
        }
        endOrder(currentLatLng?.latitude!!, currentLatLng?.longitude!!, total_amount, dis.toInt())
        /* LoginPref.ReadLogin().auth_key?.let { it1 ->
             //Toast.makeText(requireContext(),"ended",Toast.LENGTH_SHORT).show()
             viewModel.orderEnded(//it1
                 Constants.token,
                 EndDefinitionModel(
                     order_id,
                     currentLatLng?.latitude,
                     currentLatLng?.longitude,
                     dis,
                     total_amount
                 )
             )
         }*/
        binding.apply {
            linearLayout4.background =
                getDrawable(requireContext(), R.drawable.outlinebackgroundyellow)
            startButton.setTextColor(getColor(requireContext(), R.color.white))
        }
        start0rstop = true
        binding.startButton.setText("Hisobni boshlash")
    }

    private fun start() {
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
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationcallback,
            Looper.getMainLooper()
        )
    }

    private fun stop() {
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
                    Toast.makeText(requireContext(), "ruxsat berilmagan", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun instantiateWebSocket() {
        val client = OkHttpClient()
        val request =
            Request.Builder().url("ws://taxi.biznesgoya.uz/ws").build()
        val socketListener = SocketListener(requireActivity())
        webSocket = client.newWebSocket(request, socketListener)
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
        val locationRequest = LocationRequest().setInterval(500).setFastestInterval(500)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                try {
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
                } catch (e: Exception) {
                }

                googleMap.isMyLocationEnabled = true
                if (currentLatLng == null) {
                    for (location in locationResult.locations) {
                        if (currentLatLng == null) {
                            currentLatLng = LatLng(location.latitude, location.longitude)
                            googleMap.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        location.latitude,
                                        location.longitude
                                    ), 7f
                                )
                            )
                        }
                    }
                }
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
        val phone_number = phoneNumber
        if (phone_number.trim().length > 0) {
            if (checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    REQUES_CALL
                )
            } else {
                val dial = "tel:" + phone_number
                startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        map.let {
            googleMap = it
        }
        /* googleMap.moveCamera(
             CameraUpdateFactory.newLatLngZoom(
                 LatLng(
                     40.62056706525722,
                     71.59180004271276), 7f))*/
    }

    fun getOrderId() {
        ViewModelProvider(requireActivity())[OrderIdShareVM::class.java].id.observe(requireActivity(),
            {
                order_id = it.id.toInt()
                phoneNumber = it.clientphone
            })
    }

    private fun beginOrder(begin_lat: Double, begin_long: Double) {
        try {
            val body = JsonObject()
            body.addProperty("command", "begin_order")
            body.addProperty("token", Constants.token)
            body.addProperty("order_id", order_id.toString())
            body.addProperty("begin_lat", begin_lat.toString())
            body.addProperty("begin_long", begin_long.toString())
            webSocket!!.send(body.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e("location", e.toString())
        }
    }

    private fun endOrder(end_lat: Double, end_long: Double, total_amount: Int, distanse: Int) {
        try {
            val body = JsonObject()
            body.addProperty("command", "end_order")
            body.addProperty("token", Constants.token)
            body.addProperty("order_id", order_id.toString())
            body.addProperty("end_lat", end_lat.toString())
            body.addProperty("end_long", end_long.toString())
            body.addProperty("total_amount", total_amount)
            body.addProperty("distance", distanse)
            webSocket!!.send(body.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e("location", e.toString())
        }
    }

    private fun socketVM() {
        var viewmodel = ViewModelProvider(requireActivity()).get(ScoketViewModel::class.java)
        viewmodel.jsonarray.observe(viewLifecycleOwner, {
            Log.e("salom", it.toString())
        })
    }

    private fun showPath(latLngList: List<LatLng>) {
        val builder = LatLngBounds.Builder()
        for (latLng in latLngList) {
            builder.include(latLng)
        }
        val bounds = builder.build()
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 2))
        val polylineOptions = PolylineOptions()
        polylineOptions.color(Color.GRAY)
        polylineOptions.width(5f)
        polylineOptions.addAll(latLngList)
        greyPolyLine = googleMap.addPolyline(polylineOptions)
        val blackPolylineOptions = PolylineOptions()
        blackPolylineOptions.width(5f)
        blackPolylineOptions.color(Color.BLACK)
        blackPolyline = googleMap.addPolyline(blackPolylineOptions)
        originMarker = addOriginDestinationMarkerAndGet(latLngList[0])
        originMarker?.setAnchor(0.5f, 0.5f)
        destinationMarker = addOriginDestinationMarkerAndGet(latLngList[latLngList.size - 1])
        destinationMarker?.setAnchor(0.5f, 0.5f)
        val polylineAnimator = AnimationUtils.polyLineAnimator()
        polylineAnimator.addUpdateListener { valueAnimator ->
            val percentValue = (valueAnimator.animatedValue as Int)
            val index = (greyPolyLine?.points!!.size * (percentValue / 100.0f)).toInt()
            blackPolyline?.points = greyPolyLine?.points!!.subList(0, index)
        }
        polylineAnimator.start()
    }

    private fun addOriginDestinationMarkerAndGet(latLng: LatLng): Marker {
        val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(MapUtils.getDestinationBitmap())
        return googleMap.addMarker(
            MarkerOptions().position(latLng).flat(true).icon(bitmapDescriptor)
        )
    }

    fun Timer(): CountDownTimer {
        //    timer.onFinish()
        var time = 0
        var min = 0
        val timer = object : CountDownTimer(12000000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                time++
                if (time > 59) {
                    min++
                    time = 0
                }
                try {
                    if (time <= 9) {
                        binding.min.setText("0$min : 0$time ")
                    } else {
                        binding.min.setText("0$min : $time ")
                    }
                } catch (e: Exception) {
                }

            }

            override fun onFinish() {

            }
        }
        timer.start()
        return timer
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
        Timer().cancel()
        //  timer.onFinish()

    }

    override fun onResume() {
        super.onResume()
        setConnectivityListener(this)
        if (!requireContext().checkConnection()) {
            alertDialog("Internetingizni tekshiring")
        }
    }

    fun alertDialog(message:String) {
        alert(message, "") {
            positiveButton("Qaytadan") {
                if (!requireContext().checkConnection()) {
                    alertDialog("Internetingizni tekshiring")
                } else {
                    instantiateWebSocket()
                }
            }
            negativeButton("Cancel") {
                // Do negative stuff here
            }
        }.show()
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        alertDialog("Internetingizni tekshiring")
    }
    private fun CheckSocketConnect() {
        ViewModelProvider(requireActivity())[SocketErrorVM::class.java].errorLiveData.observe(viewLifecycleOwner,{
            if (!it){
                Log.e("xatolikda",it.toString())
                trynumber++
                if (trynumber<5){
                    instantiateWebSocket()
                }else{
                    alertDialog("Serverga ulanishda xatolik")
                    ViewModelProvider(requireActivity())[SocketErrorVM::class.java].setError(true)
                }
                //  Log.e("tryNumber",trynumber.toString())
            }else{

            }
        })
    }

}
