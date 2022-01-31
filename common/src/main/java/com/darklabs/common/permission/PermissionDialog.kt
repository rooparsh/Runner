package com.darklabs.common.permission

import androidx.compose.foundation.layout.*
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
fun PermissionDialog(
    locationPermissionsState: MultiplePermissionsState,
    helperText: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onRationaleStateChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = helperText)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(onClick = { locationPermissionsState.launchMultiplePermissionRequest() }) {
                Text(text = positiveButtonText)
            }
            Button(onClick = { onRationaleStateChange(true) }) {
                Text(text = negativeButtonText)
            }
        }
    }
}