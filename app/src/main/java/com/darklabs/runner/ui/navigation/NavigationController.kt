package com.darklabs.runner.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.darklabs.location.navigation.TrackingCommand
import com.darklabs.navigation.NavigationManager
import com.darklabs.runner.ui.screen.TrackingScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi

/**
 * Created by Rooparsh Kalia on 06/02/22
 */

@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@Composable
fun NavigationController(
    navController: NavHostController = rememberAnimatedNavController(),
    navigationManager: NavigationManager
) {

    navigationManager.commands.collectAsState().value.also { command ->
        if (command.destination.isNotEmpty()) {
            navController.navigate(command.destination)
        }

        AnimatedNavHost(
            navController = navController,
            startDestination = TrackingCommand.destination
        ) {
            composable(TrackingCommand.destination) {
                TrackingScreen(viewModel = hiltViewModel())
            }
        }
    }
}