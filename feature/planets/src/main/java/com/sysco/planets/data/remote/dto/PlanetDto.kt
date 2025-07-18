package com.sysco.planets.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlanetDto(
    val climate: String,
    val gravity: String,
    val name: String,
    @SerialName("orbital_period")
    val orbitalPeriod: String,
)