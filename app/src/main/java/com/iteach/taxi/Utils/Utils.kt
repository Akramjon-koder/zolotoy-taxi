package com.iteach.taxi.Utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.provider.OpenableColumns
import android.view.View
import com.google.android.material.snackbar.Snackbar
import info.texnoman.texnomart.utils.CheckInternet.ConnectivityListener
import info.texnoman.texnomart.utils.CheckInternet.ConnectivityReceiver

fun ContentResolver.getFileName(uri: Uri): String {
    var name = ""
    val cursor = query(uri, null, null, null, null)
    cursor?.use {
        it.moveToFirst()
        name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
    }
    return name

}
fun View.showSnack(isConnected: Boolean) {
    val message: String
    val color: Int
    if (isConnected) {
        // message = "Awesome!! Connected to Internet"
        color = Color.GREEN
    } else {
        message = "Internetingiz o'chiq"
        color = Color.RED
        val snack = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        snack.show()
    }
}
fun setConnectivityListener(listener: ConnectivityListener) {
    ConnectivityReceiver.connectivityListener = listener
}
fun Context.checkConnection(): Boolean {
    return ConnectivityReceiver.isConnected(this)
}


/*private fun setUpLocationListener() {
    fusedLocationProviderClient = FusedLocationProviderClient(requireActivity())
    // for getting the current location update after every 2 seconds
    val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000)
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            try {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
                    return

                }
            }catch (e:Exception){}

            googleMap.isMyLocationEnabled = true
            if (currentLatLng == null) {

                for (location in locationResult.locations) {
                    if (currentLatLng == null) {
                        //currentLatLng = LatLng(location.latitude, location.longitude)
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
*/


