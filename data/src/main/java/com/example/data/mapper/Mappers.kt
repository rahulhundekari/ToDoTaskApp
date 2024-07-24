package com.example.data.mapper

import com.example.data.model.ToDoTask
import com.example.domain.model.Task


fun List<ToDoTask>.toTask(): List<Task> = map {
    Task(
        id = it.id,
        title = it.title
    )
}
