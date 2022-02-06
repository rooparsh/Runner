package com.darklabs.location.navigation

import androidx.navigation.NamedNavArgument
import com.darklabs.navigation.NavigationCommand

/**
 * Created by Rooparsh Kalia on 06/02/22
 */
object TrackingCommand : NavigationCommand {
    override val arguments: List<NamedNavArgument> = emptyList()
    override val destination: String = "tracking"
}