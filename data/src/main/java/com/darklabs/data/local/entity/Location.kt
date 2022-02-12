package com.darklabs.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

/**
 * Created by Rooparsh Kalia on 31/01/22
 */
@Entity(
    tableName = "location",
    foreignKeys = [
        ForeignKey(
            entity = Run::class,
            parentColumns = ["id"],
            childColumns = ["runId"],
            onDelete = CASCADE
        )]
)
internal data class Location(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var runId: Long = 0L,
    var timestamp: Long = 0L,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
