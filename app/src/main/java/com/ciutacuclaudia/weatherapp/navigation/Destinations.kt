package com.ciutacuclaudia.weatherapp.navigation

sealed class Destination(
    val route: String
) {
    data object SplashScreen : Destination(route = "splashScreen")
    data object MainScreen : Destination(route = "mainScreen")
}