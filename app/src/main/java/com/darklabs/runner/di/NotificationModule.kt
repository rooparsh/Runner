package com.darklabs.runner.di

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import com.darklabs.runner.ui.MainActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Rooparsh Kalia on 05/02/22
 */

@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@Module
@InstallIn(ServiceComponent::class)
object NotificationModule {

    @Provides
    @ServiceScoped
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    @Provides
    @ServiceScoped
    fun provideLauncherActivityPendingIntent(@ApplicationContext context: Context): PendingIntent =
        PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    @Provides
    @ServiceScoped
    fun provideBaseNotificationBuilder(
        notificationManager: NotificationManager,
        @ApplicationContext context: Context
    ) = com.darklabs.location.manager.notification.NotificationManager(notificationManager, context)


    @Provides
    @ServiceScoped
    fun providesNotificationBuilder(
        notificationManager: com.darklabs.location.manager.notification.NotificationManager,
        pendingIntent: PendingIntent
    ) =
        notificationManager.buildNotification(pendingIntent)
}