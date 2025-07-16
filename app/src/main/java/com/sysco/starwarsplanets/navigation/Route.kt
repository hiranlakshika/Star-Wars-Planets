package com.sysco.starwarsplanets.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object PlanetsGraph : Route

    @Serializable
    data object PlanetsList : Route

    @Serializable
    data class PlanetDetails(val name: String) : Route
}