package com.darklabs.location.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.darklabs.domain.repository.LocationRepository
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


@AndroidEntryPoint
class LocationService : LifecycleService() {

    companion object {
        const val SMALLEST_DISPLACEMENT = 50f
        const val LOCATION_UPDATE_INTERVAL = 1000L
        const val FASTEST_LOCATION_INTERVAL = 1000L
    }

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var locationRepository: LocationRepository

    private var isTracking: Boolean = false
    private var isFirstRun = true

    private var runId = -1L


    private val locationCallback = object : LocationCallback() {

        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            if (isTracking) {
                result.locations.forEach { addPathPoint(it) }
            }

        }

    }

    private val locationRequest = LocationRequest.create().apply {
        smallestDisplacement = SMALLEST_DISPLACEMENT
        interval = LOCATION_UPDATE_INTERVAL
        fastestInterval = FASTEST_LOCATION_INTERVAL
        priority = PRIORITY_HIGH_ACCURACY
    }


    private fun startForegroundService() {
        isTracking = true

        updateTrackingLocation(isTracking)

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
                locationRepository.insertLocation(runId, it.latitude, it.longitude)

            }
        }
    }


    @SuppressLint("MissingPermission")
    fun updateTrackingLocation(isTracking: Boolean) {
        if (isTracking) {
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
                Action.ACTION_START_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        createRunInDB()
                        startForegroundService()
                    } else {
                        resumeCurrentRun()
                    }
                }
                Action.ACTION_STOP_SERVICE -> {
                    stopCurrentRun()
                    updateTrackingLocation(false)
                    this.stopSelf()
                }
                Action.ACTION_PAUSE_SERVICE -> {
                    stopCurrentRun()
                    updateTrackingLocation(false)
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun stopCurrentRun() {
        isFirstRun = false
        updateRunStatus(false)
    }

    private fun createRunInDB() {
        lifecycleScope.launch {
            runId = locationRepository.createRun()
        }
    }

    private fun resumeCurrentRun() {
        updateRunStatus(true)
    }

    private fun updateRunStatus(isRunning: Boolean) {
        lifecycleScope.launch {
            locationRepository.updateRunStatus(runId, isRunning)
        }
    }
}