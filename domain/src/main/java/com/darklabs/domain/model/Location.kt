package com.darklabs.domain.model

/**
 * Created by Rooparsh Kalia on 12/02/22
 */

data class Location(
    val id: Long? = null,
    val runId: Long = 0L,
    val timestamp: Long = 0L,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)