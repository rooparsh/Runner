package com.darklabs.location.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.darklabs.data.local.dao.LocationDao
import com.darklabs.location.notification.NOTIFICATION_ID
import com.darklabs.location.notification.buildNotification
import com.darklabs.location.notification.createNotificationChannel
import com.darklabs.location.util.Action
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Rooparsh Kalia on 04/02/22
 */

typealias LocationEntity = com.darklabs.data.local.entity.Location

@AndroidEntryPoint
class LocationService : LifecycleService() {


    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @Inject
    lateinit var locationDao: LocationDao

    companion object {
        const val LOCATION_UPDATE_INTERVAL = 1000L
        const val FASTEST_LOCATION_INTERVAL = 1000L
    }

    private var _isTracking: Boolean = false
    private var isFirstRun = true


    private val locationCallback = object : LocationCallback() {

        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            if (_isTracking) {
                result.locations.forEach { addPathPoint(it) }
            }

        }

    }

    private val locationRequest = LocationRequest.create().apply {
        interval = LOCATION_UPDATE_INTERVAL
        fastestInterval = FASTEST_LOCATION_INTERVAL
        priority = PRIORITY_HIGH_ACCURACY
    }


    private fun startForegroundService() {
        _isTracking = true
        updateTrackingLocation(_isTracking, true)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }


        startForeground(
            NOTIFICATION_ID,
            buildNotification(this).build()
        )
    }

    private fun addPathPoint(location: Location?) {
        location?.let {
            lifecycleScope.launch {
                locationDao.clearAndInsertLocation(
                    LocationEntity(it.latitude, it.longitude)
                )
            }

        }
    }


    @SuppressLint("MissingPermission")
    fun updateTrackingLocation(isTracking: Boolean, isLocationPermissionGranted: Boolean) {
        if (isTracking && isLocationPermissionGranted) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } else {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (Action.findActionFromString(it.action)) {
                Action.ACTION_START_SERVICE -> {
                    if (isFirstRun) {
                        startForegroundService()
                    } else {
                        Log.d("LocationService:", "service resume ")
                    }
                }
                Action.ACTION_STOP_SERVICE -> TODO()
                Action.ACTION_PAUSE_SERVICE -> TODO()
                null -> {}
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}