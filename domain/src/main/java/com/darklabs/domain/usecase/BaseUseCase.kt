package com.darklabs.domain.usecase

import kotlinx.coroutines.flow.Flow

/**
 * Created by Rooparsh Kalia on 22/02/22
 */
interface BaseUseCase<in Params, out Result> {
    fun observe(): Flow<Result> = throw NotImplementedError()
    suspend fun perform(): Result = throw NotImplementedError()
    suspend fun perform(params: Params): Result = throw NotImplementedError()
}