package com.darklabs.navigation

import androidx.navigation.NamedNavArgument

/**
 * Created by Rooparsh Kalia on 06/02/22
 */


object DefaultCommand : NavigationCommand {
    override val arguments: List<NamedNavArgument> = emptyList()
    override val destination: String = ""
}