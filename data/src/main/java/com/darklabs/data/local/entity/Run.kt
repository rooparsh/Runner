package com.darklabs.data.local.entity

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Rooparsh Kalia on 31/01/22
 */
@Entity(tableName = "run")
data class Run(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var img: Bitmap? = null,
    var timestamp: Long = 0L,
    var avgSpeedInKMH: Float = 0f,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long = 0L,
    var caloriesBurned: Int = 0,
    var isRunning: Boolean = false
)
