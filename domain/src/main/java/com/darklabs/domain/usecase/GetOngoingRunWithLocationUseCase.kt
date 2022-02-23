package com.darklabs.domain.usecase

import com.darklabs.domain.model.RunWithLocation
import com.darklabs.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Rooparsh Kalia on 22/02/22
 */

class GetOngoingRunWithLocationUseCase @Inject constructor(private val locationRepository: LocationRepository) :
    BaseUseCase<Unit, RunWithLocation?> {

    override fun observe(): Flow<RunWithLocation?> {
        return locationRepository.getOngoingRunWithLocation()
    }
}