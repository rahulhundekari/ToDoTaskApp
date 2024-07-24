package com.example.todotask.di

import com.example.ui.utils.CoroutineDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCoroutineDispatcherProvider() : CoroutineDispatcherProvider {
        return CoroutineDispatcherProvider()
    }
}