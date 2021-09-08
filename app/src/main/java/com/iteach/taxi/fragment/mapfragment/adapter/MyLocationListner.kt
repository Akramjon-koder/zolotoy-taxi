package com.iteach.taxi.fragment.mapfragment.adapter

import android.location.Location
import android.os.Bundle
import com.google.android.gms.location.LocationListener
import com.google.android.gms.maps.model.LatLng

class MyLocationListener : LocationListener {
    override fun onLocationChanged(location: Location) {

        //Set marker here
        val pos = LatLng(location.getLatitude(), location.getLongitude())
//            map.addMarker(
//                MarkerOptions().position(pos).icon(BitmapDescriptorFactory.fromResource(markericon))
//            )
    }

    fun onProviderDisabled(provider: String?) {}
    fun onProviderEnabled(provider: String?) {}
    fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
}