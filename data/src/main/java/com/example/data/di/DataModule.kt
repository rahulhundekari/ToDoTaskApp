package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.ToDoDatabase
import com.example.data.db.ToDoTaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideToDoDatabase(@ApplicationContext appContext: Context): ToDoDatabase {
        return Room.databaseBuilder(
            appContext,
            ToDoDatabase::class.java,
            "todo_task_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideTaskCategoryDao(toDoDatabase: ToDoDatabase): ToDoTaskDao {
        return toDoDatabase.toDoTaskDao
    }
}