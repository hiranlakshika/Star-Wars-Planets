package com.sysco.planetdetails.data.repository

import android.util.Log
import com.sysco.planetdetails.domain.repository.PlanetDetailsRepository
import com.sysco.shared.core.data.local.LocalPlanetDao
import com.sysco.shared.core.data.local.mappers.toPlanet
import com.sysco.shared.core.domain.model.Planet
import com.sysco.shared.core.domain.model.Result
import com.sysco.shared.core.domain.model.error.DataError
import com.sysco.shared.core.domain.model.error.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class PlanetDetailsRepositoryImpl @Inject constructor(private val localPlanetDao: LocalPlanetDao) :
    PlanetDetailsRepository {
    override suspend fun getLocalPlanetByName(name: String): Flow<Result<Planet, Error>> {
        return try {
            localPlanetDao.getPlanetByName(name)?.toPlanet()?.let {
                flowOf(Result.Success(it))
            } ?: flowOf(Result.Error(DataError.LocalDataError.NOT_FOUND))
        } catch (e: Exception) {
            Log.e(
                "PlanetDetailsRepositoryImpl",
                "Error fetching planet by name '$name': ${e.message}"
            )
            flowOf(Result.Error(DataError.LocalDataError.NOT_FOUND))
        }
    }
}