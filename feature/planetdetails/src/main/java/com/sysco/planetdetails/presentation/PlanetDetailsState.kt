package com.sysco.planetdetails.presentation

import com.sysco.shared.core.domain.model.Planet
import com.sysco.shared.core.presentation.UiText


data class PlanetDetailsState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val planet: Planet? = null,
    val imageId: Int? = null
)
