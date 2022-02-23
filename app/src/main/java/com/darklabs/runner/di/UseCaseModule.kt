package com.darklabs.runner.di

import com.darklabs.domain.repository.LocationRepository
import com.darklabs.domain.usecase.GetOngoingRunWithLocationUseCase
import com.darklabs.domain.usecase.InsertLocationUseCase
import com.darklabs.domain.usecase.InsertRunUseCase
import com.darklabs.domain.usecase.UpdateRunUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Rooparsh Kalia on 05/02/22
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetOngoingRunWithLocationUseCase(locationRepository: LocationRepository): GetOngoingRunWithLocationUseCase =
        GetOngoingRunWithLocationUseCase(locationRepository)

    @Provides
    @Singleton
    fun provideInsertLocationUseCase(locationRepository: LocationRepository): InsertLocationUseCase =
        InsertLocationUseCase(locationRepository)

    @Provides
    @Singleton
    fun provideInsertRunUseCase(locationRepository: LocationRepository): InsertRunUseCase =
        InsertRunUseCase(locationRepository)

    @Provides
    @Singleton
    fun provideUpdateRunUseCase(locationRepository: LocationRepository): UpdateRunUseCase =
        UpdateRunUseCase(locationRepository)
}