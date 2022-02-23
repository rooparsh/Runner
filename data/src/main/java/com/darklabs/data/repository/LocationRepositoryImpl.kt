package com.darklabs.data.repository

import com.darklabs.data.local.dao.LocationDao
import com.darklabs.data.local.dao.RunDao
import com.darklabs.data.local.entity.Location
import com.darklabs.data.local.entity.Run
import com.darklabs.data.mapper.toUiLocation
import com.darklabs.data.mapper.toUiRun
import com.darklabs.data.mapper.toUiRunWithLocation
import com.darklabs.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.darklabs.domain.model.Location as UiLocation
import com.darklabs.domain.model.Run as UiRun
import com.darklabs.domain.model.RunWithLocation as UiRunWithLocation

/**
 * Created by Rooparsh Kalia on 12/02/22
 */


internal class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val runDao: RunDao
) : LocationRepository {

    override suspend fun updateRunStatus(runId: Long, isRunning: Boolean) {
        runDao.updateRun(Run(id = runId, isRunning = isRunning))
    }

    override suspend fun createRun(): Long {
        val currentTimeInMillis = System.currentTimeMillis()
        return runDao.createNewRun(Run(timestamp = currentTimeInMillis))
    }

    override suspend fun getOngoingRun(): UiRun? {
        return runDao.getOngoingRun()?.toUiRun()
    }

    override fun getLatestLocation(runId: Long): Flow<UiLocation> {
        return locationDao.getLatestLocation(runId).map { it.toUiLocation() }
    }

    override fun getOngoingRunWithLocation(): Flow<UiRunWithLocation?> {
        return runDao.getOnGoingRunWithLocation().map { it?.toUiRunWithLocation() }
    }

    override suspend fun insertLocation(runId: Long, latitude: Double, longitude: Double): Long {
        val currentTimeInMillis = System.currentTimeMillis()

        return locationDao.insertLocation(
            Location(
                runId = runId,
                timestamp = currentTimeInMillis,
                latitude = latitude,
                longitude = longitude
            )
        )
    }
}