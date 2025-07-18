package com.sysco.planets.data.repository

import android.util.Log
import com.sysco.planets.data.remote.PlanetsApi
import com.sysco.planets.data.remote.mappers.toPlanet
import com.sysco.planets.domain.repository.PlanetsRepository
import com.sysco.shared.core.data.local.LocalPlanetDao
import com.sysco.shared.core.data.local.PlanetEntity
import com.sysco.shared.core.data.local.mappers.toPlanet
import com.sysco.shared.core.data.local.mappers.toPlanetEntity
import com.sysco.shared.core.domain.model.Planet
import com.sysco.shared.core.domain.model.Result
import com.sysco.shared.core.domain.model.error.DataError
import com.sysco.shared.core.domain.model.error.Error
import com.sysco.shared.network.util.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlanetsRepositoryImpl @Inject constructor(
    private val planetsApi: PlanetsApi,
    private val localPlanetDao: LocalPlanetDao
) :
    PlanetsRepository {

    val tag = "PlanetsRepositoryImpl"

    override suspend fun getPlanets(): Result<List<Planet>, Error> {
        return safeCall {
            val result = planetsApi.getPlanets().map { it.toPlanet() }
            localPlanetDao.upsertAll(result.map {
                it.toPlanetEntity()
            })
            result
        }
    }

    override suspend fun getLocalPlanets(): Flow<Result<List<Planet>, Error>> {
        return localPlanetDao.getLocalPlanets()
            .map<List<PlanetEntity>, Result<List<Planet>, Error>> { planetEntities ->
                Result.Success(planetEntities.map { it.toPlanet() })
            }
            .catch { e ->
                Log.e(tag, "getLocalPlanets: ${e.message}")
                emit(Result.Error(DataError.LocalDataError.NOT_FOUND))
            }
    }
}