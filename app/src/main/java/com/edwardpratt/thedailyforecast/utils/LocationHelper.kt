package com.edwardpratt.thedailyforecast.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.util.Locale

// Class for finding the location of the device
class LocationHelper(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)


    // Function to get the current location of the device
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

    // Function to request a new location update
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

    // Function to get the city name from latitude and longitude
    private fun getCityName(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())

        return try {
            // Request more results
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 5)
            if (addresses != null && addresses.isNotEmpty()) {
                // Try to get city name (locality)
                var city = addresses[0].locality
                if (city.isNullOrEmpty()) {
                    // If no city found, try admin area, sub-admin area, or sub locality
                    city = addresses[0].adminArea
                    if (city.isNullOrEmpty()) {
                        city = addresses[0].subAdminArea ?: addresses[0].subLocality
                    }
                }
                city ?: "Unknown Location"
            } else {
                "Unknown Location"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Unknown Location"
        }
    }


}
