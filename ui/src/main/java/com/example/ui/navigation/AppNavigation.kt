package com.example.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.addToDo.AddToDoScreen
import com.example.ui.addToDo.AddToDoViewModel
import com.example.ui.home.HomeScreen
import com.example.ui.home.HomeViewModel


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val snackBarHostState = remember { SnackbarHostState() }


    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

        composable(route = Screen.HomeScreen.route) { entry ->
            val viewModel = hiltViewModel<HomeViewModel>()
            val state by viewModel.state.collectAsState()
            val searchText by viewModel.searchText.collectAsState()
            val isSearching by viewModel.isSearching.collectAsState()
            val todoTasks by viewModel.todoTasks.collectAsState()
            val onResult = entry.savedStateHandle.get<Boolean>("RESULT")
            HomeScreen(
                state = state,
                snackBarHostState = snackBarHostState,
                searchText = searchText,
                isSearching = isSearching,
                onSearchTextChange = viewModel::onSearchTextChange,
                callLoadData = viewModel::fetchTodoList,
                todoTasks = todoTasks,
                addToDoTask = {
                    navController.navigate(Screen.AddToDoScreen.route)
                },
                onResult = onResult
            )
        }

        composable(route = Screen.AddToDoScreen.route) {
            val viewModel = hiltViewModel<AddToDoViewModel>()
            val addToDoState by viewModel.state.collectAsState()
            AddToDoScreen(
                state = addToDoState,
                addToDoEvent = viewModel.tasksEvent,
                onEvent = viewModel::onEvent,
                onResult = {
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "RESULT", it
                    )
                    navController.navigateUp()
                },
                onGoBack = {
                    navController.navigateUp()
                }
            )
        }

    }
}