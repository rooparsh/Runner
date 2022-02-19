package com.darklabs.data.mapper

import com.darklabs.data.local.entity.Location
import com.darklabs.data.local.entity.Run
import com.darklabs.data.local.entity.RunWithLocation
import com.darklabs.domain.model.Location as UiLocation
import com.darklabs.domain.model.Run as UiRun
import com.darklabs.domain.model.RunWithLocation as UiRunWithLocation

/**
 * Created by Rooparsh Kalia on 12/02/22
 */

internal fun Location.toUiLocation(): UiLocation {
    return with(this) {
        UiLocation(id, runId, timestamp, latitude, longitude)
    }
}

internal fun Run.toUiRun(): UiRun {
    return with(this) {
        UiRun(
            id,
            timestamp,
            avgSpeedInKMH,
            distanceInMeters,
            timeInMillis,
            caloriesBurned,
            isRunning
        )
    }
}

internal fun RunWithLocation.toUiRunWithLocation(): UiRunWithLocation {
    return with(this) {
        UiRunWithLocation(run.toUiRun(), locations.map { it.toUiLocation() })
    }
}