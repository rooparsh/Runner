package com.darklabs.location.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.darklabs.common.permission.LocationPermissionController
import com.darklabs.location.component.MapComponent
import com.darklabs.location.screen.tracking.MainViewModel
import com.darklabs.location.util.Action
import com.darklabs.location.util.sendCommandToService
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Rooparsh Kalia on 12/02/22
 */


@ExperimentalCoroutinesApi
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
            }

        }
    }
}