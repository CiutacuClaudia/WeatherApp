package com.ciutacuclaudia.weatherapp.navigation

interface Destinations {
    val route: String
}

object MainScreenDestination : Destinations {
    override val route: String = "main_screen"
}