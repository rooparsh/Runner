package com.darklabs.location.util

import android.content.Context
import android.content.Intent
import com.darklabs.location.service.LocationService
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Rooparsh Kalia on 04/02/22
 */

enum class Action(val stringAction: String) {
    ACTION_START_RESUME_SERVICE("ACTION_START_RESUME_SERVICE"),
    ACTION_STOP_SERVICE("ACTION_STOP_SERVICE"),
    ACTION_PAUSE_SERVICE("ACTION_PAUSE_SERVICE");

    companion object {
        fun findActionFromString(action: String?): Action {
            return values().find { action == it.stringAction } ?: ACTION_STOP_SERVICE
        }
    }
}

@ExperimentalCoroutinesApi
fun Context.sendCommandToService(action: Action) {
    Intent(this, LocationService::class.java).also {
        it.action = action.stringAction
        this.startService(it)
    }
}