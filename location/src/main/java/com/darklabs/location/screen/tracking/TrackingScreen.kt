package com.darklabs.location.screen.tracking

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.darklabs.common.permission.LocationPermissionController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Rooparsh Kalia on 12/02/22
 */


@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalPermissionsApi
@Composable
fun TrackingScreen(viewModel: TrackingViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        LocationPermissionController {
            MapComponent(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                viewModel = viewModel
            )
        }
        TrackingControls(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
    }
}