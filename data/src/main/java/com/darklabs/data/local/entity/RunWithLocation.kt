package com.darklabs.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by Rooparsh Kalia on 06/02/22
 */
data class RunWithLocation(
    @Embedded val run: Run,
    @Relation(
        parentColumn = "id",
        entityColumn = "runId"
    )
    val locations: List<Location>
)
