package com.darklabs.location.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Intent
import android.location.Location
import android.os.Build
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.darklabs.domain.repository.LocationRepository
import com.darklabs.location.location.LocationManager
import com.darklabs.location.notification.NOTIFICATION_ID
import com.darklabs.location.notification.buildNotification
import com.darklabs.location.notification.createNotificationChannel
import com.darklabs.location.util.Action
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Rooparsh Kalia on 04/02/22
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LocationService : LifecycleService() {

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var locationRepository: LocationRepository

    @Inject
    lateinit var locationManager: LocationManager

    private var isFirstRun = true

    private var runId = -1L

    private fun startForegroundService() {
        updateTrackingLocation(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        startForeground(
            NOTIFICATION_ID,
            buildNotification(this).build()
        )
    }

    private suspend fun addPathPoint(location: Location) {
        locationRepository.insertLocation(runId, location.latitude, location.longitude)
    }


    @SuppressLint("MissingPermission")
    fun updateTrackingLocation(isTracking: Boolean) {
        lifecycleScope.launch(Dispatchers.IO) {
            locationManager.locationFlow().collect {
                if (isTracking) {
                    addPathPoint(it)
                }
            }
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