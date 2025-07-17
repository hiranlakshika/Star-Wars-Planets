package com.sysco.starwarsplanets.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sysco.planetdetails.presentation.PlanetDetailsScreen
import com.sysco.planets.presentation.PlanetListScreen
import com.sysco.planets.presentation.PlanetListViewModel
import com.sysco.planets.presentation.PlanetsEvent

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
                val viewModel = hiltViewModel<PlanetListViewModel>()
                PlanetListScreen(onEvent = { event ->
                    when (event) {
                        is PlanetsEvent.OnSelectPlanet -> {
                            navController.navigate(Route.PlanetDetails(event.planet))
                        }

                        else -> viewModel.onEvent(event)
                    }
                })
            }
            composable<Route.PlanetDetails> { backStackEntry ->
                val planetName = backStackEntry.toRoute<Route.PlanetDetails>().name
                PlanetDetailsScreen(onNavigateBack = { navController.popBackStack() })
            }
        }
    }
}