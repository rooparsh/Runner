package com.darklabs.data.di

import com.darklabs.data.repository.LocationRepositoryImpl
import com.darklabs.domain.repository.LocationRepository
import com.darklabs.data.local.dao.LocationDao
import com.darklabs.data.local.dao.RunDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Rooparsh Kalia on 12/02/22
 */

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Provides
    @Singleton
    fun provideLocationRepository(runDao: RunDao, locationDao: LocationDao): LocationRepository =
        LocationRepositoryImpl(locationDao, runDao)
}