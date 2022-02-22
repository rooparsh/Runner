package com.darklabs.domain.usecase

/**
 * Created by Rooparsh Kalia on 22/02/22
 */
interface BaseUseCase<in Params, out Result> {
    fun perform(): Result = throw NotImplementedError()
    suspend fun performSuspend(): Result = throw NotImplementedError()
    suspend fun perform(params: Params): Result = throw NotImplementedError()
}