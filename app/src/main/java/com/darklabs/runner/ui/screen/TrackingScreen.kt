package com.darklabs.runner.ui.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.darklabs.common.permission.LocationPermissionController
import com.darklabs.location.util.Action
import com.darklabs.location.util.sendCommandToService
import com.darklabs.runner.ui.MainViewModel
import com.darklabs.runner.ui.component.MapComponent
import com.darklabs.runner.util.defaultLocation
import com.google.accompanist.permissions.ExperimentalPermissionsApi

/**
 * Created by Rooparsh Kalia on 05/02/22
 */

@ExperimentalAnimationApi
@ExperimentalPermissionsApi
@Composable
fun TrackingScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        LocationPermissionController {
            MapComponent(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                viewModel = viewModel
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Column {

                Row {
                    Button(onClick = {
                        context.sendCommandToService(Action.ACTION_START_RESUME_SERVICE)
                    }) {
                        Text(text = "START")
                    }

                    Button(onClick = {
                        context.sendCommandToService(Action.ACTION_PAUSE_SERVICE)
                    }) {
                        Text(text = "PAUSE")
                    }

                    Button(onClick = {
                        context.sendCommandToService(Action.ACTION_STOP_SERVICE)
                    }) {
                        Text(text = "STOP")
                    }
                }


                val currentLocation by viewModel.currentLocationFlow.collectAsState(initial = defaultLocation)
                Text(text = "$currentLocation")
            }

        }
    }
}