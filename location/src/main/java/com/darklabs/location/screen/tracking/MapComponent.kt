package com.darklabs.location.screen.tracking

import android.graphics.Color
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.darklabs.location.R
import com.darklabs.location.util.defaultLocation
import com.darklabs.location.util.getMarkerIconFromDrawable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

/**
 * Created by Rooparsh Kalia on 12/02/22
 */


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


@Composable
private fun Map(
    modifier: Modifier = Modifier,
    mapProperties: MapProperties = MapProperties(),
    uiSettings: MapUiSettings = MapUiSettings(),
    startLatLong: LatLng? = null,
    currentLatLong: LatLng? = null,
    route: List<LatLng>? = null,
    zoomLevel: Float = 15f,
    onMapLoaded: () -> Unit = {}
) {
    val currentCameraPositionState = rememberCameraPositionState()

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
                position = it,
                title = "My starting position",
                icon = getMarkerIconFromDrawable(
                    ContextCompat.getDrawable(
                        LocalContext.current, R.drawable.ic_location
                    )?.apply { setTint(Color.GREEN) }!!
                )
            )
        }
        currentLatLong?.let {
            Marker(
                position = it,
                title = "My current position",
                icon = getMarkerIconFromDrawable(
                    ContextCompat.getDrawable(
                        LocalContext.current, R.drawable.ic_runner
                    )?.apply { setTint(Color.RED) }!!
                )
            )
        }
        route?.let {
            Polyline(points = it, color = androidx.compose.ui.graphics.Color.Blue)
        }
    }
}