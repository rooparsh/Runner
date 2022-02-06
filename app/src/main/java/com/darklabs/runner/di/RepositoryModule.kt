package com.darklabs.runner.di

import com.darklabs.data.local.dao.LocationDao
import com.darklabs.data.local.dao.RunDao
import com.darklabs.domain.repository.LocationRepository
import com.darklabs.domain.repository.LocationRepositoryImpl
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
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLocationRepository(runDao: RunDao, locationDao: LocationDao): LocationRepository =
        LocationRepositoryImpl(locationDao, runDao)
}