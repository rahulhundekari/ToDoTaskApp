package com.example.ui.addToDo

sealed class AddToDOUiEvent {
    data class OnEditText(val text: String) : AddToDOUiEvent()
    data object OnAddToDo : AddToDOUiEvent()
}