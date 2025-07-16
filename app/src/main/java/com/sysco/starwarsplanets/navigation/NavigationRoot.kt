package com.sysco.starwarsplanets.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sysco.planetdetails.presentation.PlanetDetailsScreen
import com.sysco.planets.presentation.PlanetListScreen

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.PlanetsGraph
    ) {
        navigation<Route.PlanetsGraph>(
            startDestination = Route.PlanetsList,
        ) {
            composable<Route.PlanetsList> {
                PlanetListScreen()
            }
            composable<Route.PlanetDetails> { backStackEntry ->
                val symbol = backStackEntry.toRoute<Route.PlanetDetails>().name
                PlanetDetailsScreen {
                    navController.popBackStack()
                }
            }
        }
    }
}