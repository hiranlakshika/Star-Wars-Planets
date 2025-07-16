package com.sysco.planets.domain.repository

import com.sysco.common.model.Result
import com.sysco.common.model.error.Error
import com.sysco.planets.domain.model.Planet

interface PlanetsRepository {
    suspend fun getPlanets(): Result<List<Planet>, Error>
}