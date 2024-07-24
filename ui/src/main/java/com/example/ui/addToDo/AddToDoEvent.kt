package com.example.ui.addToDo

sealed class AddToDoEvent {
    data class OnResult(val success: Boolean) : AddToDoEvent()
}