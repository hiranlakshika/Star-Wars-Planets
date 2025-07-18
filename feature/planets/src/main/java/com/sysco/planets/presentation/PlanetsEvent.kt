package com.sysco.planets.presentation

sealed interface PlanetsEvent {
    data class OnSelectPlanet(val planet: String, val imageId: Int) : PlanetsEvent
}