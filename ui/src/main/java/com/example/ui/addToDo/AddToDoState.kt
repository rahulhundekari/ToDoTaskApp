package com.example.ui.addToDo

data class AddToDoState(
    val isLoadingAddTask: Boolean = false,
    var toDoText: String = ""
)