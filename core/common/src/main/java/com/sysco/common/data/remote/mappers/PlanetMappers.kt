package com.sysco.common.data.remote.mappers

import com.sysco.common.data.remote.dto.PlanetDto
import com.sysco.common.domain.model.Planet

internal fun PlanetDto.toPlanet() = Planet(
    name = name,
    orbitalPeriod = orbitalPeriod,
    climate = climate,
    gravity = gravity,
)