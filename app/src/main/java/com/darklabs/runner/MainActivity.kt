package com.darklabs.runner

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.darklabs.common.permission.LocationPermissionController
import com.darklabs.location.component.Map
import com.darklabs.location.util.Action
import com.darklabs.location.util.sendCommandToService
import com.darklabs.runner.ui.MainViewModel
import com.darklabs.runner.ui.theme.RunnerTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalAnimationApi
@ExperimentalPermissionsApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RunnerTheme {
                val context = LocalContext.current
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LocationPermissionController {

                        MapContent(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(), viewModel
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        Column {
                            Button(onClick = {
                                context.sendCommandToService(Action.ACTION_START_SERVICE)
                            }) {
                                Text(text = "START")
                            }

                            val currentLocation by viewModel.locationStateFlow.collectAsState(
                                initial = defaultLocation
                            )
                            Text(text = currentLocation.toString())
                        }

                    }
                }
            }
        }
    }


}

private val defaultLocation = LatLng(0.0, 0.0)


@ExperimentalAnimationApi
@Composable
fun MapContent(modifier: Modifier = Modifier, viewModel: MainViewModel) {

    val currentLocation by viewModel.locationStateFlow.collectAsState(initial = defaultLocation)

    Map(
        modifier = modifier,
        location = currentLocation,
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
