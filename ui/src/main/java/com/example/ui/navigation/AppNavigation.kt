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
import com.example.ui.home.HomeScreen
import com.example.ui.home.HomeViewModel


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val snackBarHostState = remember { SnackbarHostState() }


    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

        composable(route = Screen.HomeScreen.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
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

    }
}