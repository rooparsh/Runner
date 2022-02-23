package com.darklabs.domain.usecase

import com.darklabs.domain.model.Location
import com.darklabs.domain.repository.LocationRepository
import javax.inject.Inject

/**
 * Created by Rooparsh Kalia on 22/02/22
 */

class InsertLocationUseCase @Inject constructor(private val locationRepository: LocationRepository) :
    BaseUseCase<Location, Long> {

    override suspend fun perform(params: Location): Long {
        return with(params) {
            locationRepository.insertLocation(runId, latitude, longitude)
        }
    }
}