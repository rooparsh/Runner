package com.darklabs.common.permission

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

/**
 * Created by Rooparsh Kalia on 31/01/22
 */

@Composable
@ExperimentalPermissionsApi
fun PermissionController(
    locationPermissionsState: MultiplePermissionsState,
    rationaleState: Boolean,
    helperText: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onRationaleStateChange: (Boolean) -> Unit,
    onPermissionGranted: () -> Unit = {},
    onPermissionDenied: () -> Unit = {},
    content: @Composable () -> Unit
) {
    when {
        locationPermissionsState.allPermissionsGranted -> {
            onPermissionGranted()
            content()
        }
        locationPermissionsState.shouldShowRationale || locationPermissionsState.permissionRequested.not() -> {
            if (rationaleState) {
                Text(text = "Feature Not available")
            } else {

                PermissionDialog(
                    locationPermissionsState = locationPermissionsState,
                    helperText = helperText,
                    positiveButtonText = positiveButtonText,
                    negativeButtonText = negativeButtonText,
                    onRationaleStateChange = onRationaleStateChange
                )
            }
        }
        else -> {
            Column {
                Text(text = "Permissions denied. Please grant permissions")

                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onPermissionDenied) {
                    Text("Ok")
                }

            }
        }
    }

}