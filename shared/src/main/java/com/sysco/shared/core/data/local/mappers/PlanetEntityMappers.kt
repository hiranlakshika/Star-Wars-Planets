package com.sysco.shared.core.data.local.mappers

import com.sysco.shared.core.data.local.PlanetEntity
import com.sysco.shared.core.domain.model.Planet

fun Planet.toPlanetEntity(): PlanetEntity = PlanetEntity(
    name = name,
    climate = climate,
    gravity = gravity,
    orbitalPeriod = orbitalPeriod,
    image = image
)

fun PlanetEntity.toPlanet(): Planet = Planet(
    name = name,
    climate = climate,
    gravity = gravity,
    orbitalPeriod = orbitalPeriod,
    image = image
)