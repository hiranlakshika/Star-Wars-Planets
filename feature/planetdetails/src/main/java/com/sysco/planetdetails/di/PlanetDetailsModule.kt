package com.sysco.planetdetails.di

import com.sysco.planetdetails.data.repository.PlanetDetailsRepositoryImpl
import com.sysco.planetdetails.domain.repository.PlanetDetailsRepository
import com.sysco.shared.core.data.local.LocalPlanetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlanetDetailsModule {

    @Provides
    @Singleton
    fun providePlanetDetailsRepository(localPlanetDao: LocalPlanetDao): PlanetDetailsRepository =
        PlanetDetailsRepositoryImpl(localPlanetDao)

}