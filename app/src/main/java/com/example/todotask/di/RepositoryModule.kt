package com.example.todotask.di

import com.example.todotask.data.db.ToDoTaskDao
import com.example.todotask.data.repository.ToDoTaskRepositoryImpl
import com.example.todotask.domain.repository.TodoTaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideToDoTaskRepository(toDoTaskRepositoryImpl: ToDoTaskRepositoryImpl) : TodoTaskRepository
}