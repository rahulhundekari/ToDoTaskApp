package com.example.todotask.presentation.home

sealed class TaskEvent {
    data object OnNoteSuccess : TaskEvent()
    data object OnNoteError : TaskEvent()
}