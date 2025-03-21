package com.edwardpratt.thedailyforecast.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.*
import java.util.*

class LocationHelper(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(callback: (latitude: Double, longitude: Double, cityName: String) -> Unit) {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000 // 5 seconds
        ).setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(10000) // 10 seconds
            .setMaxUpdateDelayMillis(20000) // 20 seconds
            .build()

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location = locationResult.lastLocation
                    if (location != null) {
                        val cityName = getCityName(location.latitude, location.longitude)
                        callback(location.latitude, location.longitude, cityName)
                        fusedLocationClient.removeLocationUpdates(this) // Stop updates after first fix
                    } else {
                        callback(0.0, 0.0, "Unknown Location")
                    }
                }
            },
            Looper.getMainLooper()
        )
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocation(callback: (latitude: Double, longitude: Double, cityName: String) -> Unit) {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000 // 5 seconds
        ).setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(10000) // 10 seconds
            .setMaxUpdateDelayMillis(20000) // 20 seconds
            .build()

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location = locationResult.lastLocation
                    if (location != null) {
                        val cityName = getCityName(location.latitude, location.longitude)
                        callback(location.latitude, location.longitude, cityName)
                        fusedLocationClient.removeLocationUpdates(this)
                    } else {
                        callback(0.0, 0.0, "Unknown Location")
                    }
                }
            },
            Looper.getMainLooper()
        )
    }

    private fun getCityName(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())

        return try {
            val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)!!
            if (addresses.isNotEmpty()) {
                val city = addresses[0].locality // Try to get city name
                val country = addresses[0].countryName ?: ""
                if (!city.isNullOrEmpty()) city else addresses[0].adminArea ?: country
            } else {
                "Unknown Location"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Unknown Location"
        }
    }
}
