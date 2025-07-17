package com.sysco.shared.core.di

import com.sysco.shared.core.data.PlanetsRepositoryImpl
import com.sysco.shared.core.data.remote.PlanetsApi
import com.sysco.shared.core.domain.repository.PlanetsRepository
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
    fun providePlanetsRepository(api: PlanetsApi): PlanetsRepository = PlanetsRepositoryImpl(api)
}