package com.sysco.shared.core.data

import com.sysco.shared.core.data.remote.PlanetsApi
import com.sysco.shared.core.data.remote.mappers.toPlanet
import com.sysco.shared.core.domain.model.Planet
import com.sysco.shared.core.domain.model.Result
import com.sysco.shared.core.domain.model.error.Error
import com.sysco.shared.core.domain.repository.PlanetsRepository
import com.sysco.shared.network.util.safeCall
import javax.inject.Inject

class PlanetsRepositoryImpl @Inject constructor(private val planetsApi: PlanetsApi) :
    PlanetsRepository {
    override suspend fun getPlanets(): Result<List<Planet>, Error> {
        return safeCall { planetsApi.getPlanets().map { it.toPlanet() } }
    }
}