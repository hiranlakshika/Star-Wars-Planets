package com.sysco.planets.presentation

import com.sysco.common.ui.UiText
import com.sysco.planets.domain.model.Planet

data class PlanetsState(
    val planets: List<Planet> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
)
