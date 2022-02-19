package com.darklabs.location.manager.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.darklabs.location.R
import com.darklabs.location.service.LocationService
import com.darklabs.location.util.Action
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * Created by Rooparsh Kalia on 12/02/22
 */
class NotificationManager @Inject constructor(
    private val notificationManager: NotificationManager,
    private val context: Context
) {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
        const val NOTIFICATION_CHANNEL_NAME = "Tracking"
        const val NOTIFICATION_ID = 1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager.createNotificationChannel(channel)
    }

    @ExperimentalCoroutinesApi
    fun buildNotification(pendingIntent: PendingIntent) =
        NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_runner)
            .setContentTitle("Tracking Run")
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentText("00:00:00")
            .setAutoCancel(false)
            .setOnlyAlertOnce(true)
            .setColorized(true)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)


    @ExperimentalCoroutinesApi
    fun updateNotification(
        builder: NotificationCompat.Builder,
        context: Context,
        isActive: Boolean
    ) = builder
        .clearActions()
        .addAction(
            getIcon(isActive),
            context.getNotificationActionText(isActive),
            context.getPendingIntent(isActive)
        )

    @ExperimentalCoroutinesApi
    private fun Context.getPendingIntent(isActive: Boolean): PendingIntent {
        return if (isActive) {
            val pauseIntent = Intent(this, LocationService::class.java)
                .apply { action = Action.ACTION_PAUSE_SERVICE.stringAction }
            PendingIntent.getService(this, 1, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        } else {
            val resumeIntent = Intent(this, LocationService::class.java)
                .apply { action = Action.ACTION_START_RESUME_SERVICE.stringAction }
            PendingIntent.getService(this, 2, resumeIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    private fun getIcon(isActive: Boolean) = if (isActive) {
        R.drawable.ic_pause
    } else {
        R.drawable.ic_resume
    }

    private fun Context.getNotificationActionText(isActive: Boolean) = if (isActive) {
        getString(R.string.action_pause)
    } else {
        getString(R.string.action_resume)
    }

    fun notify(builder: NotificationCompat.Builder) {
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}