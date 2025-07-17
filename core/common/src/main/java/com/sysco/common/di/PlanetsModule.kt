package com.sysco.common.di

import com.sysco.common.data.PlanetsRepositoryImpl
import com.sysco.common.data.remote.PlanetsApi
import com.sysco.common.domain.repository.PlanetsRepository
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