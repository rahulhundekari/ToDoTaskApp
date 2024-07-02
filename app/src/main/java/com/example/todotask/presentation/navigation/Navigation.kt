package com.example.todotask.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todotask.presentation.MainViewModel
import com.example.todotask.presentation.addToDo.AddToDoScreen
import com.example.todotask.presentation.home.HomeScreen


@Composable
fun Navigation() {

    val navController = rememberNavController()

    val snackBarHostState = remember { SnackbarHostState() }


    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

        composable(route = Screen.HomeScreen.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            val state by viewModel.state.collectAsState()
            val searchText by viewModel.searchText.collectAsState()
            val isSearching by viewModel.isSearching.collectAsState()
            val todoTasks by viewModel.todoTasks.collectAsState()
            HomeScreen(
                state = state,
                todoTasks = todoTasks,
                snackBarHostState = snackBarHostState,
                callLoadData = viewModel::fetchTodoList,
                searchText = searchText,
                isSearching = isSearching,
                taskEvent = viewModel.tasksEvent,
                onSearchTextChange = viewModel::onSearchTextChange,
                addToDoTask = viewModel::storeData
            )
        }

//        composable(route = Screen.AddToDoScreen.route) {
//            val viewModel = hiltViewModel<MainViewModel>()
//            val state by viewModel.addTaskState.collectAsState()
//            AddToDoScreen(state,
//                viewModel.tasksEvent,
//                onAddToDo = { toDoText ->
//                    viewModel.storeData(toDoText)
//                },
//                onNavigateBack = { isSuccess ->
//                    navController.previousBackStackEntry?.savedStateHandle?.set(
//                        "ERROR_KEY",
//                        isSuccess
//                    )
//                    navController.navigateUp()
//                })
//        }

    }
}