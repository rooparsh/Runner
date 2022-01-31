package com.darklabs.common.permission

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

/**
 * Created by Rooparsh Kalia on 31/01/22
 */

@Composable
@ExperimentalPermissionsApi
fun LocationPermissionController(
    onPermissionGranted: () -> Unit = {},
    onPermissionDenied: () -> Unit = {},
    content: @Composable () -> Unit
) {
    var doNotShowRationale by rememberSaveable { mutableStateOf(false) }

    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    PermissionController(
        locationPermissionsState = locationPermissionState,
        rationaleState = doNotShowRationale,
        helperText = "GPS Permission is very important for the functioning of this app.",
        positiveButtonText = "Request Permission",
        negativeButtonText = "Don't show again",
        onRationaleStateChange = { doNotShowRationale = it },
        onPermissionGranted = onPermissionGranted,
        onPermissionDenied = onPermissionDenied
    ) {
        content()
    }
}