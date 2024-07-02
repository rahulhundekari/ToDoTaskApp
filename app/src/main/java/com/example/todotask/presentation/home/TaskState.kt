package com.example.todotask.presentation.home

data class TaskState(
    val isEmptyTodoTasks: Boolean = false,
    val isLoadingAddTask: Boolean = false
)