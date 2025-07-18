package com.sysco.planets.data.repository

import android.util.Log
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.sysco.planets.data.remote.PlanetsApi
import com.sysco.planets.data.remote.dto.PlanetDto
import com.sysco.shared.core.data.local.LocalPlanetDao
import com.sysco.shared.core.data.local.PlanetEntity
import com.sysco.shared.core.domain.model.Result
import com.sysco.shared.core.domain.model.error.DataError
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mockStatic
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException

class PlanetsRepositoryImplTest {

    private lateinit var planetsRepository: PlanetsRepositoryImpl
    private val planetsApi: PlanetsApi = mock()
    private val localPlanetDao: LocalPlanetDao = mock()

    @Before
    fun setUp() {
        planetsRepository = PlanetsRepositoryImpl(planetsApi, localPlanetDao)
    }

    @Test
    fun `getPlanets returns success and saves to db`() = runTest {

        val planetDto = PlanetDto(
            "arid",
            "2014-12-09T13:50:49.641000Z",
            "Tatooine",
            "2014-12-20T20:58:18.411000Z",
        )
        val responseDto = listOf(planetDto)
        whenever(planetsApi.getPlanets()).thenReturn(responseDto)

        val result = planetsRepository.getPlanets()

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data.first().name).isEqualTo("Tatooine")
        verify(localPlanetDao).upsertAll(any())
    }

    @Test
    fun `getPlanets returns error when api throws exception`() = runTest {

        whenever(planetsApi.getPlanets()).thenAnswer { throw IOException() }

        val result = planetsRepository.getPlanets()

        assertThat(result).isInstanceOf(Result.Error::class.java)
    }

    @Test
    fun `getLocalPlanets returns success with data from db`() = runTest {

        val planetEntity = PlanetEntity(
            "Tatooine",
            "arid",
            "1 standard",
            "304",
            "https://swapi.dev/api/planets/1/"
        )
        whenever(localPlanetDao.getLocalPlanets()).thenReturn(flowOf(listOf(planetEntity)))

        planetsRepository.getLocalPlanets().test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data.first().name).isEqualTo("Tatooine")
            awaitComplete()
        }
    }

    @Test
    fun `getLocalPlanets returns error when db throws exception and logs the error`() = runTest {
        // 1. Arrange: Wrap the test logic in mockStatic
        mockStatic(Log::class.java).use { mockedLog ->
            val errorMessage = "DB error"
            whenever(localPlanetDao.getLocalPlanets()).thenReturn(flow {
                throw RuntimeException(
                    errorMessage
                )
            })

            // 2. Act: Collect the flow using Turbine
            planetsRepository.getLocalPlanets().test {
                // 3. Assert: Check the emitted result
                val result = awaitItem()
                assertThat(result).isInstanceOf(Result.Error::class.java)
                assertThat((result as Result.Error).error).isEqualTo(DataError.LocalDataError.NOT_FOUND)
                awaitComplete()
            }

            // 4. Verify: Check that Log.e was called with the correct message
            mockedLog.verify { Log.e(any(), eq("getLocalPlanets: $errorMessage")) }
        }
    }
}
