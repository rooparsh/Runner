package com.darklabs.runner.ui.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.darklabs.location.component.Map
import com.darklabs.runner.ui.MainViewModel
import com.darklabs.runner.util.defaultLocation
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings

/**
 * Created by Rooparsh Kalia on 05/02/22
 */

@ExperimentalAnimationApi
@Composable
fun MapComponent(modifier: Modifier = Modifier, viewModel: MainViewModel) {

    val currentLocation by viewModel.currentLocationFlow.collectAsState(initial = defaultLocation)

    Map(
        modifier = modifier,
        currentLatLong = currentLocation,
        mapProperties = MapProperties(
            mapType = MapType.NORMAL,
            isMyLocationEnabled = true
        ),
        uiSettings = MapUiSettings(
            compassEnabled = true,
            myLocationButtonEnabled = true
        )
    )
}