package com.example.ui.home

sealed class TaskEvent {
    data object OnNoteSuccess : TaskEvent()
    data object OnNoteError : TaskEvent()
}