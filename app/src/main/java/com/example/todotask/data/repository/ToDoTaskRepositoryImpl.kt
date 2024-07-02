package com.example.todotask.data.repository

import com.example.todotask.data.db.ToDoTaskDao
import com.example.todotask.data.model.ToDoTask
import com.example.todotask.domain.repository.TodoTaskRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class ToDoTaskRepositoryImpl @Inject constructor(
    private val toDoTaskDao: ToDoTaskDao
) : TodoTaskRepository {

    override suspend fun getToDoData(): Flow<List<ToDoTask>> {
        return toDoTaskDao.getTasks()
    }

    override suspend fun getToDoDataByName(searchQuery: String): Flow<List<ToDoTask>> {
        return toDoTaskDao.getAllTaskByName(searchQuery)
    }

    override suspend fun storeToDoData(todoText: String) {
        toDoTaskDao.insertTodoTask(ToDoTask(title = todoText))
    }
}