package com.darklabs.common.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Rooparsh Kalia on 19/02/22
 */
interface CoroutineDispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}