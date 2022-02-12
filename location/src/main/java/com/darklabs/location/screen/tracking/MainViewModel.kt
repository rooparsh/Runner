package com.darklabs.location.screen.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darklabs.domain.model.Location
import com.darklabs.domain.model.RunWithLocation
import com.darklabs.domain.repository.LocationRepository
import com.darklabs.location.util.defaultLocation
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Rooparsh Kalia on 05/02/22
 */

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private var _latestLocationFlow = MutableStateFlow(defaultLocation)
    val currentLocationFlow: StateFlow<LatLng> = _latestLocationFlow

    private var _pathFlow = MutableStateFlow<List<LatLng>>(emptyList())
    val pathFlow: StateFlow<List<LatLng>> = _pathFlow


    init {
        fetchLatestLocation()
        fetchOnGoingRunPath()
    }

    private fun fetchOnGoingRun(): Flow<RunWithLocation> {
        return locationRepository
            .getOngoingRunWithLocation()
            .filterNotNull()
    }

    private fun fetchOnGoingRunLocations(): Flow<List<Location>> {
        return fetchOnGoingRun()
            .map { data -> data.locations }
            .filter { it.isNotEmpty() }
    }

    private fun fetchLatestLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchOnGoingRunLocations()
                .map { listOfLocations -> listOfLocations.last() }
                .distinctUntilChanged()
                .map { location ->
                    LatLng(location.latitude, location.longitude)
                }
                .collect {
                    _latestLocationFlow.value = it
                }
        }
    }

    private fun fetchOnGoingRunPath() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchOnGoingRunLocations()
                .map { locations ->
                    locations.map { location ->
                        LatLng(
                            location.latitude,
                            location.longitude
                        )
                    }
                }
                .collect {
                    _pathFlow.value = it
                }
        }
    }
}