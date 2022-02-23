package com.darklabs.domain.usecase

import com.darklabs.domain.model.request.UpdateRun
import com.darklabs.domain.repository.LocationRepository
import javax.inject.Inject

/**
 * Created by Rooparsh Kalia on 22/02/22
 */

class UpdateRunUseCase @Inject constructor(private val locationRepository: LocationRepository) :
    BaseUseCase<UpdateRun, Unit> {

    override suspend fun perform(params: UpdateRun) {
        return locationRepository.updateRunStatus(params.runId, params.isRunning)
    }
}