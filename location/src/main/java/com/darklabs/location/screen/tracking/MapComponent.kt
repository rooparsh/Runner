package com.darklabs.location.screen.tracking

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.darklabs.location.component.Map
import com.darklabs.location.util.defaultLocation
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Rooparsh Kalia on 12/02/22
 */


@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun MapComponent(modifier: Modifier = Modifier, viewModel: TrackingViewModel) {

    val currentLocation by viewModel.currentLocationFlow.collectAsState(initial = defaultLocation)
    val path by viewModel.pathFlow.collectAsState(initial = null)

    Map(
        modifier = modifier,
        startLatLong = path?.firstOrNull(),
        currentLatLong = currentLocation,
        mapProperties = MapProperties(
            mapType = MapType.NORMAL,
            isMyLocationEnabled = true
        ),
        route = path,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            compassEnabled = true,
            myLocationButtonEnabled = true
        )
    )
}