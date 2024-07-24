package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.ToDoTask

@Database(
    entities = [ToDoTask::class],
    version = 1
)
abstract class ToDoDatabase : RoomDatabase() {
    abstract val toDoTaskDao: ToDoTaskDao
}