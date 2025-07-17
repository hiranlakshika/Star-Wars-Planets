package com.sysco.shared.core.di

import android.content.Context
import androidx.room.Room
import com.sysco.shared.core.data.local.LocalPlanetDao
import com.sysco.shared.core.data.local.PlanetsDatabase
import com.sysco.shared.core.data.remote.PlanetsApi
import com.sysco.shared.core.data.repository.PlanetsRepositoryImpl
import com.sysco.shared.core.domain.repository.PlanetsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlanetsModule {

    @Provides
    @Singleton
    fun providePlanetsApi(retrofit: Retrofit): PlanetsApi = retrofit.create(PlanetsApi::class.java)

    @Provides
    @Singleton
    fun providePlanetsRepository(
        api: PlanetsApi,
        localPlanetDao: LocalPlanetDao
    ): PlanetsRepository = PlanetsRepositoryImpl(api, localPlanetDao)

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