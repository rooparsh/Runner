package com.darklabs.location.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.darklabs.location.R

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

fun buildNotification(context: Context) =
    NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_runner)
        .setContentTitle("Location Service")
        .setDefaults(NotificationCompat.DEFAULT_ALL)
        .setContentText("00:00:00")
        .setAutoCancel(false)
        .setContentIntent(context.getPendingIntent())
        .setOngoing(true)
        .setPriority(NotificationCompat.PRIORITY_MAX)

private fun Context.getPendingIntent() = PendingIntent.getActivity(
    this,
    0,
    Intent(),
    PendingIntent.FLAG_IMMUTABLE
)


