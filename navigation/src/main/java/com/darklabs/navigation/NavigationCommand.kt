package com.darklabs.navigation

import androidx.navigation.NamedNavArgument

/**
 * Created by Rooparsh Kalia on 06/02/22
 */
interface NavigationCommand {
    val arguments: List<NamedNavArgument>
    val destination: String
}
