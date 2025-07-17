package com.sysco.shared.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalPlanetDao {
    @Upsert
    suspend fun upsert(planet: PlanetEntity)

    @Query("SELECT * FROM PlanetEntity")
    fun getLocalPlanets(): Flow<List<PlanetEntity>>

    @Query("SELECT * FROM PlanetEntity WHERE name = :name")
    suspend fun getPlanetByName(name: String): PlanetEntity?
}