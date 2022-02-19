package com.darklabs.location.manager.location

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

/**
 * Created by Rooparsh Kalia on 12/02/22
 */
class LocationManager @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {

    companion object {
        const val SMALLEST_DISPLACEMENT = 50f
        const val LOCATION_UPDATE_INTERVAL = 1000L
        const val FASTEST_LOCATION_INTERVAL = 1000L
    }


    private val locationRequest = LocationRequest.create().apply {
        smallestDisplacement = SMALLEST_DISPLACEMENT
        interval = LOCATION_UPDATE_INTERVAL
        fastestInterval = FASTEST_LOCATION_INTERVAL
        priority = PRIORITY_HIGH_ACCURACY
    }

    @ExperimentalCoroutinesApi
    @SuppressLint("MissingPermission")
    private val _locationUpdates = callbackFlow {
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                trySend(result.lastLocation)
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            callback,
            Looper.getMainLooper()
        ).addOnFailureListener { e ->
            close(e)
        }

        awaitClose {
            fusedLocationProviderClient.removeLocationUpdates(callback)
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(): LatLng? {
        var currentLocation: LatLng? = null
        fusedLocationProviderClient
            .getCurrentLocation(PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
            .addOnSuccessListener {
                it?.let { safeLoc ->
                    currentLocation = LatLng(safeLoc.latitude, safeLoc.longitude)
                }
            }

        return currentLocation
    }

    @ExperimentalCoroutinesApi
    fun locationFlow(): Flow<Location> = _locationUpdates
}