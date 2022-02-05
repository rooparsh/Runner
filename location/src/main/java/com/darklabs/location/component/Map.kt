package com.darklabs.location.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.darklabs.location.R
import com.darklabs.location.util.getMarkerIconFromDrawable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

/**
 * Created by Rooparsh Kalia on 31/01/22
 */

@Composable
fun Map(
    modifier: Modifier = Modifier,
    mapProperties: MapProperties = MapProperties(),
    uiSettings: MapUiSettings = MapUiSettings(),
    location: LatLng,
    zoomLevel: Float = 11f,
    onMapLoaded: () -> Unit = {}
) {
    val cameraPositionState = rememberCameraPositionState()
    LaunchedEffect(location) {
        cameraPositionState.animate(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.fromLatLngZoom(
                    location,
                    zoomLevel
                )
            )
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = uiSettings,
        onMapLoaded = onMapLoaded,
        googleMapOptionsFactory = {
            GoogleMapOptions().camera(cameraPositionState.position)
        }) {
        Marker(
            position = cameraPositionState.position.target,
            title = "My current Location",
            icon = getMarkerIconFromDrawable(
                ContextCompat.getDrawable(
                    LocalContext.current, R.drawable.ic_runner
                )!!
            )
        )
    }
}