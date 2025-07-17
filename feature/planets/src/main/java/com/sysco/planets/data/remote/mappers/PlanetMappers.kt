package com.sysco.planets.data.remote.mappers

import com.sysco.planets.data.remote.dto.PlanetDto
import com.sysco.shared.core.domain.model.Planet

internal fun PlanetDto.toPlanet() = Planet(
    name = name,
    orbitalPeriod = orbitalPeriod,
    climate = climate,
    gravity = gravity,
)