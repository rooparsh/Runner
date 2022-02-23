package com.darklabs.domain.repository

import com.darklabs.domain.model.Location
import com.darklabs.domain.model.Run
import com.darklabs.domain.model.RunWithLocation
import kotlinx.coroutines.flow.Flow

/**
 * Created by Rooparsh Kalia on 06/02/22
 */
interface LocationRepository {

    suspend fun updateRunStatus(runId: Long, isRunning: Boolean)
    suspend fun createRun(): Long
    suspend fun getOngoingRun(): Run?
    fun getLatestLocation(runId: Long): Flow<Location>
    fun getOngoingRunWithLocation(): Flow<RunWithLocation?>
    suspend fun insertLocation(runId: Long, latitude: Double, longitude: Double): Long
}


