package com.example.domain.usecase

import com.example.domain.repository.TodoTaskRepository
import javax.inject.Inject

class GetTaskListUseCase @Inject constructor(
    private val todoTaskRepository: TodoTaskRepository
) {

    suspend operator fun invoke() = todoTaskRepository.getToDoData()
}