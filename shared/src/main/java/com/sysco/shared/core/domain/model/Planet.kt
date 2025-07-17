package com.sysco.shared.core.domain.model

import com.sysco.shared.core.domain.Constants

data class Planet(
    val name: String,
    val climate: String,
    val orbitalPeriod: String,
    val gravity: String,
    val image: String = Constants.DYNAMIC_SMALL_IMAGE_URL
)