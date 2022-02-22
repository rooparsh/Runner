package com.darklabs.runner.di

import com.darklabs.domain.repository.LocationRepository
import com.darklabs.domain.usecase.GetOngoingRunWithLocationUseCase
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
}