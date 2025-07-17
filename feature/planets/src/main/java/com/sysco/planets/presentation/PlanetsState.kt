package com.sysco.planets.presentation

import com.sysco.shared.core.domain.model.Planet
import com.sysco.shared.core.presentation.UiText

data class PlanetsState(
    val planets: List<Planet> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
)
