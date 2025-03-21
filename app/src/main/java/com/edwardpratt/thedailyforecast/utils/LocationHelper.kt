package com.edwardpratt.thedailyforecast.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import java.util.Locale

class LocationHelper(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(callback: (latitude: Double, longitude: Double, cityName: String?) -> Unit) {
        val locationTask: Task<Location> = fusedLocationClient.lastLocation

        locationTask.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                val cityName = getCityName(latitude, longitude)
                callback(latitude, longitude, cityName)
            } else {
                Log.e("LocationHelper", "Failed to get location")
                callback(0.0, 0.0, null)
            }
        }.addOnFailureListener { exception ->
            Log.e("LocationHelper", "Location error: ${exception.message}")
            callback(0.0, 0.0, null)
        }
    }

    private fun getCityName(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            addresses?.get(0)?.locality
        } catch (e: Exception) {
            Log.e("LocationHelper", "Geocoder error: ${e.message}")
            null
        }
    }
}
