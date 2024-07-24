package com.example.data.repository

import com.example.data.db.ToDoTaskDao
import com.example.data.mapper.toTask
import com.example.data.model.ToDoTask
import com.example.domain.model.Task
import com.example.domain.repository.TodoTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ToDoTaskRepositoryImpl @Inject constructor(
    private val toDoTaskDao: ToDoTaskDao
) : TodoTaskRepository {

    override suspend fun getToDoData(): Flow<List<Task>> {
        return toDoTaskDao.getTasks().map {
            it.toTask()
        }
    }

    override suspend fun getToDoDataByName(searchQuery: String): Flow<List<Task>> {
        return toDoTaskDao.getAllTaskByName(searchQuery).map {
            it.toTask()
        }
    }

    override suspend fun storeToDoData(todoText: String) {
        println("Data Store called")
       val l = toDoTaskDao.insertTodoTask(ToDoTask(title = todoText)).runCatching {
           println("Data store Error occurred $")
       }
        println("Data Inserted value $l")
    }
}