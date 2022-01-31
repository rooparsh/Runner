package com.darklabs.runner

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.darklabs.common.permission.LocationPermissionController
import com.darklabs.location.component.MapView
import com.darklabs.location.util.getMarkerIconFromDrawable
import com.darklabs.runner.ui.theme.RunnerTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val iconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_runner)!!

        setContent {
            RunnerTheme {

                val defaultLocation = LatLng(0.0, 0.0)
                val currentLocation = remember { mutableStateOf(defaultLocation) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LocationPermissionController(
                        onPermissionGranted = {
                            fusedLocationProviderClient
                                .lastLocation
                                .addOnSuccessListener {
                                    it?.let { location ->
                                        currentLocation.value =
                                            LatLng(location.latitude, location.longitude)
                                    }
                                }
                        }) {
                        MapView { map ->
                            map.uiSettings.apply {
                                isZoomControlsEnabled = true
                                isCompassEnabled = true
                                isMyLocationButtonEnabled = currentLocation.value != defaultLocation
                            }

                            val markerOptions = MarkerOptions()
                                .icon(getMarkerIconFromDrawable(iconDrawable))
                                .title("My Current Location")
                                .position(currentLocation.value)
                            map.addMarker(markerOptions)

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RunnerTheme {
        Greeting("Android")
    }
}
