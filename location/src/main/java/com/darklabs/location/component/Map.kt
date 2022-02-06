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
    startLatLong: LatLng? = null,
    currentLatLong: LatLng? = null,
    route: List<LatLng>? = null,
    zoomLevel: Float = 11f,
    onMapLoaded: () -> Unit = {}
) {
    val currentCameraPositionState = rememberCameraPositionState()

    val startingCameraPositionState = startLatLong?.let {
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(startLatLong, zoomLevel)
        }
    }

    LaunchedEffect(currentLatLong) {
        currentLatLong?.let { safeCurrentLatLong ->
            currentCameraPositionState.animate(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(
                        safeCurrentLatLong,
                        zoomLevel
                    )
                )
            )
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = currentCameraPositionState,
        properties = mapProperties,
        uiSettings = uiSettings,
        onMapLoaded = onMapLoaded,
        googleMapOptionsFactory = {
            GoogleMapOptions().camera(currentCameraPositionState.position)
        }) {

        startLatLong?.let {
            Marker(
                position = startingCameraPositionState!!.position.target,
                title = "My starting position",
                icon = getMarkerIconFromDrawable(
                    ContextCompat.getDrawable(
                        LocalContext.current, R.drawable.ic_runner
                    )!!
                )
            )
        }
        Marker(
            position = currentCameraPositionState.position.target,
            title = "My current position",
            icon = getMarkerIconFromDrawable(
                ContextCompat.getDrawable(
                    LocalContext.current, R.drawable.ic_runner
                )!!
            )
        )
        route?.let {
            Polyline(points = it)
        }
    }
}