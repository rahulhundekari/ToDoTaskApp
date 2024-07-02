package com.example.todotask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.example.todotask.data.model.ToDoTask

@Database(
    entities = [ToDoTask::class],
    version = 1
)
abstract class ToDoDatabase : RoomDatabase() {
    abstract val toDoTaskDao: ToDoTaskDao
}