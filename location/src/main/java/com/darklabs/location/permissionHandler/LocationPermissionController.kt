package com.darklabs.location.permissionHandler

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.darklabs.commonui.permission.PermissionController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

/**
 * Created by Rooparsh Kalia on 19/02/22
 */

@Composable
@ExperimentalPermissionsApi
fun LocationPermissionController(
    onPermissionGranted: () -> Unit = {},
    onPermissionDenied: () -> Unit = {}
) {

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        val locationPermissionState = rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )

        val dialogState = remember { mutableStateOf(false) }

        Button(onClick = { dialogState.value = true }) {
            Text(text = "Grant Permissions")
        }

        PermissionController(
            multiplePermissionsState = locationPermissionState,
            dialogState = dialogState,
            helperText = "GPS Permission is very important for the functioning of this app.",
            positiveButtonText = "Request Permission",
            negativeButtonText = "Don't show again",
            onPermissionGranted = onPermissionGranted,
            onPermissionDenied = onPermissionDenied
        )
    }
}