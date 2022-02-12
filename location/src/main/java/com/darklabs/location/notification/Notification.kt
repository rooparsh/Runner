package com.darklabs.location.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.darklabs.location.R
import com.darklabs.location.service.LocationService
import com.darklabs.location.util.Action
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Rooparsh Kalia on 04/02/22
 */

const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
const val NOTIFICATION_CHANNEL_NAME = "Tracking"
const val NOTIFICATION_ID = 1

@RequiresApi(Build.VERSION_CODES.O)
fun createNotificationChannel(notificationManager: NotificationManager) {

    val channel = NotificationChannel(
        NOTIFICATION_CHANNEL_ID,
        NOTIFICATION_CHANNEL_NAME,
        IMPORTANCE_LOW
    )

    notificationManager.createNotificationChannel(channel)
}

@ExperimentalCoroutinesApi
fun buildNotification(context: Context, pendingIntent: PendingIntent) =
    NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_runner)
        .setContentTitle("Tracking Run")
        .setDefaults(NotificationCompat.DEFAULT_ALL)
        .setContentText("00:00:00")
        .setAutoCancel(false)
        .setContentIntent(pendingIntent)
        .setOngoing(true)
        .setPriority(NotificationCompat.PRIORITY_MAX)

fun NotificationCompat.Builder.updateNotification(context: Context, isActive: Boolean) {
    this
        .clearActions()
        .addAction(
            getIcon(isActive),
            context.getNotificationActionText(isActive),
            context.getPendingIntent(isActive)
        )
}

@ExperimentalCoroutinesApi
private fun Context.getPendingIntent(isActive: Boolean): PendingIntent {
    return if (isActive) {
        val pauseIntent = Intent(this, LocationService::class.java)
            .apply { action = Action.ACTION_PAUSE_SERVICE.stringAction }
        PendingIntent.getService(this, 1, pauseIntent, FLAG_UPDATE_CURRENT)
    } else {
        val resumeIntent = Intent(this, LocationService::class.java)
            .apply { action = Action.ACTION_START_RESUME_SERVICE.stringAction }
        PendingIntent.getService(this, 2, resumeIntent, FLAG_UPDATE_CURRENT)
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


