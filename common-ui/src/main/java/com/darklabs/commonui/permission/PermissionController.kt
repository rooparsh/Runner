package com.darklabs.commonui.permission

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*

/**
 * Created by Rooparsh Kalia on 31/01/22
 */

@Composable
@ExperimentalPermissionsApi
fun PermissionController(
    multiplePermissionsState: MultiplePermissionsState,
    dialogState: MutableState<Boolean>,
    helperText: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onPermissionGranted: () -> Unit = {},
    onPermissionDenied: () -> Unit = {}
) {
    if (multiplePermissionsState.allPermissionsGranted) {
        onPermissionGranted()
    } else {
        val allPermissionsRevoked =
            multiplePermissionsState.permissions.size == multiplePermissionsState.revokedPermissions.size

        when {
            allPermissionsRevoked -> {
                PermissionDialog(
                    multiplePermissionsState = multiplePermissionsState,
                    dialogState = dialogState,
                    helperText = helperText,
                    positiveButtonText = positiveButtonText,
                    negativeButtonText = negativeButtonText
                )
            }
            multiplePermissionsState.shouldShowRationale -> {
                Text(text = "Feature Not available")
            }

        }
    }
}

@Composable
@ExperimentalPermissionsApi
fun PermissionController(
    permissionState: PermissionState,
    helperText: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onPermissionGranted: () -> Unit = {},
    onPermissionDenied: () -> Unit = {}
) {
    val dialogState = remember { mutableStateOf(false) }
    when {
        permissionState.status.isGranted -> onPermissionGranted()
        permissionState.status.isGranted.not() -> {
            Column {
                Text(text = "Permissions denied. Please grant permissions")

                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onPermissionDenied) {
                    Text("Ok")
                }

            }
        }
        permissionState.status.shouldShowRationale -> {
            PermissionDialog(
                permissionState = permissionState,
                dialogState = dialogState,
                helperText = helperText,
                positiveButtonText = positiveButtonText,
                negativeButtonText = negativeButtonText
            )
        }

    }
}