package com.darklabs.runner.di

import com.darklabs.common.dispatcher.CoroutineDispatcherProvider
import com.darklabs.common.dispatcher.CoroutineDispatcherProviderImpl
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
object DispatcherModule {

    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcherProvider =
        CoroutineDispatcherProviderImpl()
}