package com.darklabs.domain.model

import android.graphics.Bitmap

/**
 * Created by Rooparsh Kalia on 12/02/22
 */
data class Run(
    val id: Long = 0L,
    val img: Bitmap? = null,
    val timestamp: Long = 0L,
    val avgSpeedInKMH: Float = 0f,
    val distanceInMeters: Int = 0,
    val timeInMillis: Long = 0L,
    val caloriesBurned: Int = 0,
    val isRunning: Boolean = false
)