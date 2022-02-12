package com.darklabs.domain.model

/**
 * Created by Rooparsh Kalia on 12/02/22
 */

data class RunWithLocation(
    val run: Run,
    val locations: List<Location>
)