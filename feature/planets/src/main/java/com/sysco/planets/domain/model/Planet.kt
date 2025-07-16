package com.sysco.planets.domain.model

data class Planet(
    val name: String,
    val climate: String,
    val orbitalPeriod: String,
    val gravity: String,
    val image: String = "https://picsum.photos/200"
)