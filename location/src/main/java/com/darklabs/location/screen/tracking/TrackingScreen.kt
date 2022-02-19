package com.darklabs.location.screen.tracking

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Created by Rooparsh Kalia on 12/02/22
 */


@ExperimentalAnimationApi
@Composable
fun TrackingScreen(viewModel: TrackingViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {

        MapComponent(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            viewModel = viewModel
        )

        TrackingControls(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
    }
}