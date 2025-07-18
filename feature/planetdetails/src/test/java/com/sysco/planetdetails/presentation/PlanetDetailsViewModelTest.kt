package com.sysco.planetdetails.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.sysco.planetdetails.domain.repository.PlanetDetailsRepository
import com.sysco.shared.core.domain.model.Planet
import com.sysco.shared.core.domain.model.Result
import com.sysco.shared.core.domain.model.error.DataError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PlanetDetailsViewModelTest {

    private lateinit var viewModel: PlanetDetailsViewModel
    private val planetDetailsRepository: PlanetDetailsRepository = mock()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = PlanetDetailsViewModel(planetDetailsRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onEvent OnInit updates imageId and fetches planet details`() = runTest {
        // Given
        val imageId = 123
        val planetName = "Tatooine"
        val planet =
            Planet("Tatooine", "arid", "1 standard", "304", "https://swapi.dev/api/planets/1/")
        whenever(planetDetailsRepository.getLocalPlanetByName(planetName)).thenReturn(
            flowOf(
                Result.Success(
                    planet
                )
            )
        )

        // When
        viewModel.onEvent(PlanetDetailsEvent.OnInit(planetName, imageId))

        // Then
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState.imageId).isEqualTo(imageId)
            // Assert that planet is null in the initial state
            assertThat(initialState.planet).isNull()

            val updatedState = awaitItem()
            assertThat(updatedState.imageId).isEqualTo(imageId)
            assertThat(updatedState.planet).isEqualTo(planet)
            cancelAndIgnoreRemainingEvents()
        }
        verify(planetDetailsRepository).getLocalPlanetByName(planetName)
    }

    @Test
    fun `fetchPlanetDetails updates state with success when data is found`() = runTest {
        // Given
        val planetName = "Tatooine"
        val planet =
            Planet("Tatooine", "arid", "1 standard", "304", "https://swapi.dev/api/planets/1/")
        whenever(planetDetailsRepository.getLocalPlanetByName(planetName)).thenReturn(
            flowOf(
                Result.Success(
                    planet
                )
            )
        )

        // When
        viewModel.onEvent(PlanetDetailsEvent.OnInit(planetName = planetName, imageId = 0))

        // Then
        viewModel.state.test {
            skipItems(1) // Skip initial state
            val state = awaitItem()
            assertThat(state.planet).isEqualTo(planet)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchPlanetDetails updates state with error when no data is found`() = runTest {
        // Given
        val planetName = "NonExistentPlanet"
        whenever(planetDetailsRepository.getLocalPlanetByName(planetName)).thenReturn(
            flowOf(
                Result.Error(
                    DataError.LocalDataError.NOT_FOUND
                )
            )
        )

        // When
        viewModel.onEvent(PlanetDetailsEvent.OnInit(planetName = planetName, imageId = 0))

        // Then
        viewModel.state.test {
            skipItems(1) // Skip initial state
            val state = awaitItem()
            assertThat(state.error).isNotNull()
            cancelAndIgnoreRemainingEvents()
        }
    }
}