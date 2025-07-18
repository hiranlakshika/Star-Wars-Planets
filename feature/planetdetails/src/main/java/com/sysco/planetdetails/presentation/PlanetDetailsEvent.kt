package com.sysco.planetdetails.presentation

sealed interface PlanetDetailsEvent {
    data class OnInit(val planetName: String, val imageId: Int) : PlanetDetailsEvent
}