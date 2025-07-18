package com.sysco.planetdetails.data.repository

import android.util.Log
import app.cash.turbine.test
import com.sysco.shared.core.data.local.LocalPlanetDao
import com.sysco.shared.core.data.local.PlanetEntity
import com.sysco.shared.core.domain.model.Result
import com.sysco.shared.core.domain.model.error.DataError
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mockStatic
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PlanetDetailsRepositoryImplTest {

    private lateinit var planetDetailsRepository: PlanetDetailsRepositoryImpl
    private val localPlanetDao: LocalPlanetDao = mock()

    @Before
    fun setUp() {
        planetDetailsRepository = PlanetDetailsRepositoryImpl(localPlanetDao)
    }

    @Test
    fun `getLocalPlanetByName returns success when planet found`() = runTest {
        // Given
        val planetName = "Tatooine"
        val planetEntity = PlanetEntity(
            "Tatooine",
            "arid",
            "1 standard",
            "304",
            "https://swapi.dev/api/planets/1/"
        )
        whenever(localPlanetDao.getPlanetByName(planetName)).thenReturn(planetEntity)

        // When
        planetDetailsRepository.getLocalPlanetByName(planetName).test {
            // Then
            val result = awaitItem()
            assertTrue(result is Result.Success)
            assertEquals(planetName, (result as Result.Success).data.name)
            awaitComplete()
        }
    }


    @Test
    fun `getLocalPlanetByName returns error when db throws exception`() = runTest {

        mockStatic(Log::class.java).use { mockedLog ->
            val errorMessage = "DB error"

            val planetName = "Tatooine"
            whenever(localPlanetDao.getPlanetByName(planetName)).thenThrow(RuntimeException("DB error"))

            // When
            planetDetailsRepository.getLocalPlanetByName(planetName).test {
                // Then
                val result = awaitItem()
                assertTrue(result is Result.Error)
                assertEquals(DataError.LocalDataError.NOT_FOUND, (result as Result.Error).error)
                awaitComplete()
            }

            mockedLog.verify { Log.e(any(), eq("getLocalPlanetByName: $errorMessage")) }
        }
    }
}