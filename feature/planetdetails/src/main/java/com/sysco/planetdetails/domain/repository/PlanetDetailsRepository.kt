package com.sysco.planetdetails.domain.repository

import com.sysco.shared.core.domain.model.Planet
import com.sysco.shared.core.domain.model.Result
import com.sysco.shared.core.domain.model.error.Error
import kotlinx.coroutines.flow.Flow

interface PlanetDetailsRepository {
    suspend fun getLocalPlanetByName(name: String): Flow<Result<Planet, Error>>
}