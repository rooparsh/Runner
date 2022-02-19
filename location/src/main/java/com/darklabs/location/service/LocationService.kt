package com.darklabs.location.service

import android.content.Intent
import android.location.Location
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.darklabs.common.dispatcher.CoroutineDispatcherProvider
import com.darklabs.domain.repository.LocationRepository
import com.darklabs.location.manager.location.LocationManager
import com.darklabs.location.manager.notification.NotificationManager
import com.darklabs.location.util.Action
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
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
    lateinit var dispatcherProvider: CoroutineDispatcherProvider

    @Inject
    lateinit var locationRepository: LocationRepository

    @Inject
    lateinit var locationManager: LocationManager

    @Inject
    lateinit var notificationManager: NotificationManager


    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    private var isFirstRun = true
    private var isTracking = false

    private var runId = -1L

    override fun onCreate() {
        super.onCreate()
        observeLocationUpdates()
    }

    private fun observeLocationUpdates() {
        lifecycleScope.launch(dispatcherProvider.io) {
            locationManager.locationFlow().collect {
                if (isTracking) {
                    addPathPoint(it)
                }
            }
        }
    }

    private fun startForegroundService() {
        updateTrackingLocation(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel()
        }

        startForeground(
            NotificationManager.NOTIFICATION_ID,
            notificationBuilder.build()
        )
    }

    private suspend fun addPathPoint(location: Location) {
        locationRepository.insertLocation(runId, location.latitude, location.longitude)
    }


    private fun updateTrackingLocation(isTracking: Boolean) {
        this.isTracking = isTracking
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (Action.findActionFromString(it.action)) {
                Action.ACTION_START_RESUME_SERVICE -> {
                    updateNotification(isRunning = true)
                    if (isFirstRun) {
                        createRunInDB()
                        startForegroundService()
                    } else {
                        resumeCurrentRun()
                    }
                }
                Action.ACTION_STOP_SERVICE -> {
                    stopCurrentRun()
                    updateTrackingLocation(isTracking)
                    this.stopSelf()
                }
                Action.ACTION_PAUSE_SERVICE -> {
                    stopCurrentRun()
                    updateNotification(isRunning = isTracking)
                    updateTrackingLocation(isTracking)
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun stopCurrentRun() {
        isFirstRun = false
        isTracking = false
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
        lifecycleScope.launch(dispatcherProvider.io) {
            locationRepository.updateRunStatus(runId, isRunning)
        }
    }

    private fun updateNotification(isRunning: Boolean) {
        notificationManager.updateNotification(notificationBuilder, this, isRunning)
        notificationManager.notify(notificationBuilder)
    }

    private fun startTimer() {
        CoroutineScope(dispatcherProvider.main).launch {
            while (isTracking) {

            }
        }
    }
}