package com.sysco.planets.di

import com.sysco.planets.data.remote.PlanetsApi
import com.sysco.planets.data.repository.PlanetsRepositoryImpl
import com.sysco.planets.domain.repository.PlanetsRepository
import com.sysco.shared.core.data.local.LocalPlanetDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}