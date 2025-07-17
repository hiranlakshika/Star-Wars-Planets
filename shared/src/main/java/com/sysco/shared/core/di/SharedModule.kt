package com.sysco.shared.core.di

import android.content.Context
import androidx.room.Room
import com.sysco.shared.core.data.local.LocalPlanetDao
import com.sysco.shared.core.data.local.PlanetsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedModule {

    @Provides
    @Singleton
    fun providePlanetDatabase(@ApplicationContext context: Context): PlanetsDatabase =
        Room.databaseBuilder(
            context,
            PlanetsDatabase::class.java,
            PlanetsDatabase.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideLocalPlanetDao(planetsDatabase: PlanetsDatabase): LocalPlanetDao =
        planetsDatabase.dao()
}