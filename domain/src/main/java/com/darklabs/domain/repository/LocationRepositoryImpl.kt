package com.darklabs.domain.repository

import com.darklabs.data.local.dao.LocationDao
import com.darklabs.data.local.dao.RunDao
import com.darklabs.data.local.entity.Location
import com.darklabs.data.local.entity.Run
import com.darklabs.data.local.entity.RunWithLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Rooparsh Kalia on 06/02/22
 */

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val runDao: RunDao
) : LocationRepository {

    override suspend fun updateRunStatus(runId: Long, isRunning: Boolean) {
        runDao.updateRun(Run(id = runId, isRunning = isRunning))
    }

    override suspend fun createRun(): Long {
        return runDao.createNewRun()
    }

    override suspend fun getOngoingRun(): Run? {
        return runDao.getOngoingRun()
    }

    override fun getLatestLocation(runId: Long): Flow<Location> {
        return locationDao.getLatestLocation(runId)
    }

    override fun getOngoingRunWithLocation(): Flow<RunWithLocation?> {
        return runDao.getOnGoingRunWithLocation()
    }

    override suspend fun insertLocation(runId: Long, latitude: Double, longitude: Double) {
        val currentTimeInMillis = System.currentTimeMillis()

        locationDao.insertLocation(
            Location(
                runId = runId,
                timestamp = currentTimeInMillis,
                latitude = latitude,
                longitude = longitude
            )
        )
    }

}