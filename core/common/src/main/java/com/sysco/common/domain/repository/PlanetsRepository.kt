package com.sysco.common.domain.repository

import com.sysco.common.domain.model.Planet
import com.sysco.common.domain.model.Result
import com.sysco.common.domain.model.error.Error

interface PlanetsRepository {
    suspend fun getPlanets(): Result<List<Planet>, Error>
}