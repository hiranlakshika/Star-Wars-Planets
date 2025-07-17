package com.sysco.shared.core.data.remote.mappers

import com.sysco.shared.core.data.remote.dto.PlanetDto
import com.sysco.shared.core.domain.model.Planet

internal fun PlanetDto.toPlanet() = Planet(
    name = name,
    orbitalPeriod = orbitalPeriod,
    climate = climate,
    gravity = gravity,
)