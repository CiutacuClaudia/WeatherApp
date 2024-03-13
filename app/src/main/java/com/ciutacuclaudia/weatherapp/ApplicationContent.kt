package com.ciutacuclaudia.weatherapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ciutacuclaudia.weatherapp.navigation.Destination
import com.ciutacuclaudia.weatherapp.navigation.NavHostController
import com.ciutacuclaudia.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun ApplicationContent(
    startDestination: String = Destination.SplashScreen.route,
) {
    val navController = rememberNavController()

    WeatherAppTheme {
        Scaffold { innerPadding ->
            NavHostController(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}