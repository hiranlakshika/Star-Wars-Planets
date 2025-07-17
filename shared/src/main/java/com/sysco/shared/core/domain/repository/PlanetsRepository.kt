package com.sysco.shared.core.domain.repository

import com.sysco.shared.core.domain.model.Planet
import com.sysco.shared.core.domain.model.Result
import com.sysco.shared.core.domain.model.error.Error

interface PlanetsRepository {
    suspend fun getPlanets(): Result<List<Planet>, Error>
}