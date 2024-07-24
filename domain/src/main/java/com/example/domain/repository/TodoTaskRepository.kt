package com.example.domain.repository

import com.example.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TodoTaskRepository {
    suspend fun getToDoData(): Flow<List<Task>>

    suspend fun getToDoDataByName(searchQuery: String): Flow<List<Task>>

    suspend fun storeToDoData(todoText: String)
}