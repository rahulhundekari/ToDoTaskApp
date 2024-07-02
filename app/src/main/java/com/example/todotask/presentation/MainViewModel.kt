package com.example.todotask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotask.data.model.ToDoTask
import com.example.todotask.domain.repository.TodoTaskRepository
import com.example.todotask.domain.utils.CoroutineDispatcherProvider
import com.example.todotask.presentation.home.TaskEvent
import com.example.todotask.presentation.home.TaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val toDoTaskRepository: TodoTaskRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    // for home screen
    private val _state = MutableStateFlow(TaskState())
    val state = _state.asStateFlow()

    private val taskEventChannel = Channel<TaskEvent>()
    val tasksEvent = taskEventChannel.receiveAsFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _todoTasks = MutableStateFlow(listOf<ToDoTask>())


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val todoTasks = _searchText
        .flatMapLatest { searchText ->
            if (searchText.isBlank()) {
                _isSearching.update { false }
                _todoTasks.map { it }
            } else {
                _isSearching.update { true }
                _searchText
                    .debounce(2000L)
                    .combine(_todoTasks) { debouncedSearchText, todoTask ->
                        todoTask.filter {
                            it.title.contains(debouncedSearchText, ignoreCase = true)
                        }
                    }
                    .onEach { _isSearching.update { false } }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _todoTasks.value
        )

    fun storeData(todoText: String) {
        if (todoText.isBlank()) {
            return
        }

        setLoading(true)

        if (todoText.trim().equals("Error", ignoreCase = true)) {
            viewModelScope.launch {
                delay(3000)
                setLoading(false)
                taskEventChannel.send(TaskEvent.OnNoteError)
            }
            return
        }

        viewModelScope.launch(coroutineDispatcherProvider.ioDispatcher) {
            // add 3 seconds delay to upload data
            delay(3000)
            toDoTaskRepository.storeToDoData(todoText.trim())
            setLoading(false)
            taskEventChannel.send(TaskEvent.OnNoteSuccess)
        }
    }

    private fun setLoading(loading: Boolean) {
        _state.update {
            it.copy(isLoadingAddTask = loading)
        }
    }

    fun fetchTodoList() {
        viewModelScope.launch(coroutineDispatcherProvider.ioDispatcher) {
            _isSearching.update { true }
            toDoTaskRepository.getToDoData()
                .collectLatest { todoTasks ->
                    _state.update { taskState ->
                        taskState.copy(isEmptyTodoTasks = todoTasks.isEmpty())
                    }
                    _todoTasks.value = todoTasks
                    _isSearching.update { false }
                }

        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun clearSearchText() {
        if (_searchText.value.isNotBlank())
            _searchText.value = ""
    }
}