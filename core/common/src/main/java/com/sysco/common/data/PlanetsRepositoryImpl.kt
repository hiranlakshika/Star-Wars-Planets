package com.sysco.common.data

import com.sysco.common.data.remote.PlanetsApi
import com.sysco.common.data.remote.mappers.toPlanet
import com.sysco.common.domain.model.Planet
import com.sysco.common.domain.model.Result
import com.sysco.common.domain.model.error.Error
import com.sysco.common.domain.repository.PlanetsRepository
import com.sysco.network.util.safeCall
import javax.inject.Inject

class PlanetsRepositoryImpl @Inject constructor(private val planetsApi: PlanetsApi) :
    PlanetsRepository {
    override suspend fun getPlanets(): Result<List<Planet>, Error> {
        return safeCall { planetsApi.getPlanets().map { it.toPlanet() } }
    }
}