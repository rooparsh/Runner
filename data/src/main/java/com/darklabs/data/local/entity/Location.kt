package com.darklabs.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Rooparsh Kalia on 31/01/22
 */
@Entity(tableName = "location")
data class Location(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
