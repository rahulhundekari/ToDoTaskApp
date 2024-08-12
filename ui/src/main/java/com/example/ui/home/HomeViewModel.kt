package com.example.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Task
import com.example.domain.repository.TodoTaskRepository
import com.example.domain.usecase.AddTaskUseCase
import com.example.domain.usecase.GetTaskListUseCase
import com.example.ui.utils.CoroutineDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val getTaskListUseCase: GetTaskListUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _state = MutableStateFlow(TaskState())
    val state = _state.asStateFlow()

    val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    val _todoTasks = MutableStateFlow(listOf<Task>())

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

    fun fetchTodoList() {
        viewModelScope.launch(coroutineDispatcherProvider.ioDispatcher) {
            _isSearching.update { true }
            getTaskListUseCase()
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