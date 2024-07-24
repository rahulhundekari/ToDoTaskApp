package com.example.domain.usecase

import com.example.domain.repository.TodoTaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val todoTaskRepository: TodoTaskRepository
) {
    operator fun invoke(task: String) = flow<Unit> {
        println("Data store invoke")
        todoTaskRepository.storeToDoData(task)
    }.flowOn(Dispatchers.IO)
}