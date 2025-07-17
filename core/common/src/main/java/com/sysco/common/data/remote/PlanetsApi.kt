package com.sysco.common.data.remote

import com.sysco.common.data.remote.dto.PlanetDto
import retrofit2.http.GET

interface PlanetsApi {
    @GET("api/planets")
    suspend fun getPlanets(): List<PlanetDto>
}