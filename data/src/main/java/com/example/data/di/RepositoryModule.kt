package com.example.data.di

import com.example.data.repository.ToDoTaskRepositoryImpl
import com.example.domain.repository.TodoTaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideToDoTaskRepository(toDoTaskRepositoryImpl: ToDoTaskRepositoryImpl) : TodoTaskRepository
}