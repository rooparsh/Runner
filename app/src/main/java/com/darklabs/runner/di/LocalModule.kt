package com.darklabs.runner.di

import android.content.Context
import com.darklabs.data.local.DatabaseHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Rooparsh Kalia on 05/02/22
 */
@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        DatabaseHelper.provideDatabase(context)

    @Provides
    @Singleton
    fun provideRunDao(db: DatabaseHelper.RunningDatabase) = db.getRunDao()

    @Provides
    @Singleton
    fun provideLocationDao(db: DatabaseHelper.RunningDatabase) = db.getLocationDao()

}