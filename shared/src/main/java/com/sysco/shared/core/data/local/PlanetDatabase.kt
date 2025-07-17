package com.sysco.shared.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PlanetEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PlanetsDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "planets.db"
    }

    abstract fun dao(): LocalPlanetDao
}