package com.sysco.planets.presentation

sealed interface PlanetsEvent {
    data object OnLoad : PlanetsEvent

    data class OnSelectPlanet(val planet: String) : PlanetsEvent
}