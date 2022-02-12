package com.darklabs.location.screen.tracking

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.darklabs.location.util.Action
import com.darklabs.location.util.sendCommandToService

/**
 * Created by Rooparsh Kalia on 12/02/22
 */


@Composable
fun TrackingControls(modifier: Modifier) {
    val context = LocalContext.current
    Box(
        modifier = modifier
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