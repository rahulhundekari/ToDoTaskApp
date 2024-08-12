package com.example.ui.addToDo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.AddTaskUseCase
import com.example.ui.utils.CoroutineDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AddToDoViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {


    private val _state = MutableStateFlow(AddToDoState())
    val state = _state.asStateFlow()

    private val taskEventChannel = Channel<AddToDoEvent>()
    val tasksEvent = taskEventChannel.receiveAsFlow()

    fun onEvent(event: AddToDOUiEvent) {
        when (event) {
            is AddToDOUiEvent.OnEditText -> {
                _state.update {
                    it.copy(
                        toDoText = event.text
                    )
                }
            }

            AddToDOUiEvent.OnAddToDo -> {
                storeData(_state.value.toDoText)
            }
        }
    }

    private fun storeData(todoText: String) {
        if (todoText.isBlank()) {
            return
        }

        setLoading(true)

        if (todoText.trim().equals("Error", ignoreCase = true)) {
            viewModelScope.launch {
                delay(3000)
                setLoading(false)
                taskEventChannel.send(AddToDoEvent.OnResult(false))
            }
            return
        }

        viewModelScope.launch(coroutineDispatcherProvider.ioDispatcher) {
            // add 3 seconds delay to upload data
            delay(3000)
            addTaskUseCase(todoText.trim()).launchIn(viewModelScope)
            setLoading(false)
            taskEventChannel.send(AddToDoEvent.OnResult(true))
        }
    }

    private fun setLoading(loading: Boolean) {
        _state.update {
            it.copy(isLoadingAddTask = loading)
        }
    }
}
