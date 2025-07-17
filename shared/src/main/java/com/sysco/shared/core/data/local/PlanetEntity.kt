package com.sysco.shared.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlanetEntity(
    @PrimaryKey(autoGenerate = false)
    var name: String = "",
    var climate: String = "",
    var gravity: String = "",
    var orbitalPeriod: String = "",
    var image: String = ""
)