package com.example.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.domain.model.Task
import com.example.domain.usecase.GetTaskListUseCase
import com.example.ui.utils.CoroutineDispatcherProvider
import com.example.ui.utils.TestDispatcherProvider
import com.nhaarman.mockitokotlin2.doReturn
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getTaskListUseCase: GetTaskListUseCase

    private lateinit var coroutineDispatcherProvider: CoroutineDispatcherProvider

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun openMocks() {
        coroutineDispatcherProvider = TestDispatcherProvider()
        homeViewModel = HomeViewModel(getTaskListUseCase, coroutineDispatcherProvider)
    }


    @Test
    fun `fetchTodoList should update state with fetched tasks`() =
        runTest {
            val expectedTasks = listOf(Task(0, "Task 1"), Task(1, "Task 2"))
            `when`(getTaskListUseCase()).thenReturn(flowOf(expectedTasks))

            runCatching {
                homeViewModel.fetchTodoList()

                homeViewModel.todoTasks.test {
                    val actualTasks = awaitItem()
                    assertEquals(expectedTasks, actualTasks)
                }
            }
        }

    @Test
    fun `fetchTodoList should handle empty task list`() = runTest {
        val expectedTasks = emptyList<Task>()
        `when`(getTaskListUseCase()).thenReturn(flowOf(expectedTasks))

        runCatching {
            homeViewModel.fetchTodoList()

            homeViewModel.todoTasks.test {
                val actualTasks = awaitItem()
                assertEquals(expectedTasks, actualTasks)
                assertEquals(true, homeViewModel.state.value.isEmptyTodoTasks)
                assertEquals(false, homeViewModel.isSearching.value)
            }
        }
    }


    @Test
    fun `when searchText is blank, should return all tasks and isSearching should be false`() =
        runTest {
            val initialTasks = listOf(Task(0, "Task 1"), Task(1, "Task 2"))

            runCatching {
                homeViewModel._todoTasks.update { initialTasks }
                homeViewModel._searchText.update { "" }

                val result = homeViewModel.todoTasks.first()

                assertEquals(initialTasks, result)
                assertEquals(false, homeViewModel.isSearching.value)
            }
        }

    @Test
    fun `when searchText is not blank, should filter tasks and isSearching should be true then false`() =
        runTest {
            val initialTasks = listOf(Task(0, "Buy groceries"), Task(1, "Walk the dog"))
            runCatching {
                homeViewModel._todoTasks.update { initialTasks }
                homeViewModel._searchText.update { "dog" }

                val result = homeViewModel.todoTasks.first()

                val expectedFilteredTasks = listOf(Task(1, "Walk the dog"))
                assertEquals(expectedFilteredTasks, result)
                assertEquals(false, homeViewModel.isSearching.value)
            }
        }


}