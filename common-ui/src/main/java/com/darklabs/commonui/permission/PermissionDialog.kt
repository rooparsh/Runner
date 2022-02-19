package com.darklabs.commonui.permission

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState

/**
 * Created by Rooparsh Kalia on 31/01/22
 */


@Composable
@ExperimentalPermissionsApi
fun PermissionDialog(
    multiplePermissionsState: MultiplePermissionsState,
    dialogState: MutableState<Boolean>,
    helperText: String,
    positiveButtonText: String,
    negativeButtonText: String
) {
    if (dialogState.value) {
        AlertDialog(onDismissRequest = { dialogState.value = false },
            text = { Text(text = helperText) },
            confirmButton = {
                Button(onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }) {
                    Text(text = positiveButtonText)
                }
            },
            dismissButton = {
                Button(onClick = { dialogState.value = false }) {
                    Text(text = negativeButtonText)
                }
            }
        )
    }
}

@Composable
@ExperimentalPermissionsApi
fun PermissionDialog(
    permissionState: PermissionState,
    dialogState: MutableState<Boolean>,
    helperText: String,
    positiveButtonText: String,
    negativeButtonText: String
) {
    if (dialogState.value) {
        AlertDialog(onDismissRequest = { dialogState.value = false },
            text = { Text(text = helperText) },
            confirmButton = {
                Button(onClick = { permissionState.launchPermissionRequest() }) {
                    Text(text = positiveButtonText)
                }
            },
            dismissButton = {
                Button(onClick = { dialogState.value = false }) {
                    Text(text = negativeButtonText)
                }
            }
        )
    }
}