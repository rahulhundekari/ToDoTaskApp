package com.example.todotask.domain.repository

import com.example.todotask.data.model.ToDoTask
import kotlinx.coroutines.flow.Flow

interface TodoTaskRepository {
    suspend fun getToDoData(): Flow<List<ToDoTask>>

    suspend fun getToDoDataByName(searchQuery: String): Flow<List<ToDoTask>>

    suspend fun storeToDoData(todoText: String)
}