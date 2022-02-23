package com.darklabs.domain.usecase

import com.darklabs.domain.repository.LocationRepository
import javax.inject.Inject

/**
 * Created by Rooparsh Kalia on 22/02/22
 */

class InsertRunUseCase @Inject constructor(private val locationRepository: LocationRepository) :
    BaseUseCase<Unit, Long> {

    override suspend fun perform(): Long {
        return locationRepository.createRun()
    }
}