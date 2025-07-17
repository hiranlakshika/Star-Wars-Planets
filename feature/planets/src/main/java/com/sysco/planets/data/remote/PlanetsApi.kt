package com.sysco.planets.data.remote

import com.sysco.planets.data.remote.dto.PlanetDto
import retrofit2.http.GET

interface PlanetsApi {
    @GET("api/planets")
    suspend fun getPlanets(): List<PlanetDto>
}