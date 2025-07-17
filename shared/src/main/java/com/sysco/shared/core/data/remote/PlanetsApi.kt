package com.sysco.shared.core.data.remote

import com.sysco.shared.core.data.remote.dto.PlanetDto
import retrofit2.http.GET

interface PlanetsApi {
    @GET("api/planets")
    suspend fun getPlanets(): List<PlanetDto>
}