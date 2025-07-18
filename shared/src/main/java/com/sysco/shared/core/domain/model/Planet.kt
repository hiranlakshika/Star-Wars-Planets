package com.sysco.shared.core.domain.model

data class Planet(
    val name: String,
    val climate: String,
    val orbitalPeriod: String,
    val gravity: String,
    val image: String = ""
)