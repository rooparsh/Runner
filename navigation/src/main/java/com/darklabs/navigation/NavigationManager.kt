package com.darklabs.navigation

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by Rooparsh Kalia on 06/02/22
 */
class NavigationManager {

    val commands = MutableStateFlow<NavigationCommand>(DefaultCommand)

    fun navigate(direction: NavigationCommand) {
        commands.value = direction
    }
}