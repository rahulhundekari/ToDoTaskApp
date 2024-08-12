package com.example.ui.di

import com.example.ui.utils.CoroutineDispatcherProvider
import com.example.ui.utils.DefaultCoroutineDispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal abstract class AppModule {

    @Binds
    abstract fun provideDefaultCoroutineDispatcher(
        defaultCoroutineDispatcherProvider:
        DefaultCoroutineDispatcherProvider
    ): CoroutineDispatcherProvider
}