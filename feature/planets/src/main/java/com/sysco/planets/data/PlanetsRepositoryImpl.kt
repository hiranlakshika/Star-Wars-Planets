package com.sysco.planets.data

import com.sysco.common.model.Result
import com.sysco.common.model.error.Error
import com.sysco.network.util.safeCall
import com.sysco.planets.data.remote.PlanetsApi
import com.sysco.planets.data.remote.mappers.toPlanet
import com.sysco.planets.domain.model.Planet
import com.sysco.planets.domain.repository.PlanetsRepository
import javax.inject.Inject

class PlanetsRepositoryImpl @Inject constructor(private val planetsApi: PlanetsApi) :
    PlanetsRepository {
    override suspend fun getPlanets(): Result<List<Planet>, Error> {
        return safeCall { planetsApi.getPlanets().map { it.toPlanet() } }
    }
}