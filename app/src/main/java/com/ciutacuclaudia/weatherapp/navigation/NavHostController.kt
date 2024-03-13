package com.ciutacuclaudia.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ciutacuclaudia.weatherapp.ui.screens.MainScreen
import com.ciutacuclaudia.weatherapp.ui.screens.SplashScreen
import com.ciutacuclaudia.weatherapp.viewmodel.MainViewModel

@Composable
fun NavHostController(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: String = Destination.SplashScreen.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(route = Destination.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Destination.MainScreen.route) {
            val mainViewModel: MainViewModel = hiltViewModel()
            MainScreen(viewModel = mainViewModel)
        }
    }
}