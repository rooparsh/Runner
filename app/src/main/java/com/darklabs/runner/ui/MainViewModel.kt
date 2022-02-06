package com.darklabs.runner.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darklabs.domain.repository.LocationRepository
import com.darklabs.runner.util.defaultLocation
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Rooparsh Kalia on 05/02/22
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private var _latestLocationFlow = MutableStateFlow(defaultLocation)
    val currentLocationFlow: StateFlow<LatLng> = _latestLocationFlow


    init {
        fetchOngoingRunWithLocation()
    }

    private fun fetchOngoingRunWithLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.getOngoingRunWithLocation()
                .filterNotNull()
                .map { data -> data.locations.sortedByDescending { it.timestamp } }
                .filter { it.isNotEmpty() }
                .map { listOfLocations -> listOfLocations[0] }
                .map { location ->
                    LatLng(location.latitude, location.longitude)
                }
                .collect {
                    _latestLocationFlow.value = it
                }
        }
    }
}