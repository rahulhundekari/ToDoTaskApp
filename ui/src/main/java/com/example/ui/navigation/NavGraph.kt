package com.example.ui.navigation

sealed class Screen(val route:String) {

    data object HomeScreen : Screen("home_screen")
    data object AddToDoScreen : Screen("add_todo_screen")

}

