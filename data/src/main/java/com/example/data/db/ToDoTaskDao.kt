package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoTask(toDoTask: ToDoTask): Long

    @Query("SELECT * FROM todo_table")
    fun getTasks(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table WHERE title LIKE '%' || :searchQuery")
    fun getAllTaskByName(searchQuery: String): Flow<List<ToDoTask>>
}