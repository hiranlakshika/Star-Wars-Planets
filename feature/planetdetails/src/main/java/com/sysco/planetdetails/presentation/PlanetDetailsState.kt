package com.sysco.planetdetails.presentation

import com.sysco.common.domain.model.Planet
import com.sysco.common.presentation.UiText

data class PlanetDetailsState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val planet: Planet? = null
)
