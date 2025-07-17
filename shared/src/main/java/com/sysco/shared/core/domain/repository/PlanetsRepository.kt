package com.sysco.shared.core.domain.repository

import com.sysco.shared.core.domain.model.Planet
import com.sysco.shared.core.domain.model.Result
import com.sysco.shared.core.domain.model.error.Error
import kotlinx.coroutines.flow.Flow

interface PlanetsRepository {
    suspend fun getPlanets(): Result<List<Planet>, Error>

    suspend fun getLocalPlanets(): Flow<Result<List<Planet>, Error>>

    suspend fun getLocalPlanetByName(name: String): Flow<Result<Planet, Error>>
}