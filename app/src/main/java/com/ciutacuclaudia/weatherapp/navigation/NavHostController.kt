package com.ciutacuclaudia.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ciutacuclaudia.weatherapp.ui.screens.MainScreen

@Composable
fun NavHostController(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(route = MainScreenDestination.route) {
            MainScreen()
        }
    }
}