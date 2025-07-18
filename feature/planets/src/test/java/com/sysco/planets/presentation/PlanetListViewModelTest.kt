package com.sysco.planets.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.sysco.planets.domain.repository.PlanetsRepository
import com.sysco.shared.core.domain.model.Planet
import com.sysco.shared.core.domain.model.Result
import com.sysco.shared.core.domain.model.error.DataError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.time.Duration.Companion.seconds

@ExperimentalCoroutinesApi
class PlanetListViewModelTest {

    private lateinit var planetsRepository: PlanetsRepository

    private lateinit var viewModel: PlanetListViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    private val mockPlanets = listOf(
        Planet(
            name = "Tatooine",
            climate = "arid",
            orbitalPeriod = "304",
            gravity = "1 standard",
            image = "tatooine.jpg"
        ),
        Planet(
            name = "Alderaan",
            climate = "temperate",
            orbitalPeriod = "364",
            gravity = "1 standard",
            image = "alderaan.jpg"
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        planetsRepository = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init - emits Loading then Success with planets from both remote and local`() = runTest {

        whenever(planetsRepository.getPlanets()).thenReturn(Result.Success(mockPlanets))
        whenever(planetsRepository.getLocalPlanets()).thenReturn(
            flow { emit(Result.Success(mockPlanets)) }
        )

        viewModel = PlanetListViewModel(planetsRepository)

        viewModel.state.test(timeout = 3.seconds) {
            val state = expectMostRecentItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.planets).isEqualTo(mockPlanets)
            assertThat(state.error).isNull()
        }
    }

    @Test
    fun `fetchPlanets should show error when remote call fails`() = runTest {

        whenever(planetsRepository.getPlanets()).thenReturn(Result.Error(DataError.NetworkError.UNKNOWN))
        whenever(planetsRepository.getLocalPlanets()).thenReturn(
            flow { emit(Result.Success(mockPlanets)) }
        )

        viewModel = PlanetListViewModel(planetsRepository)

        viewModel.state.test(timeout = 3.seconds) {
            val remoteState = awaitItem()
            assertThat(remoteState.isLoading).isFalse()
            assertThat(remoteState.error).isNotNull()
            assertThat(remoteState.planets).isEmpty()

            val localState = awaitItem()
            assertThat(localState.isLoading).isFalse()
            assertThat(localState.error).isNull()
            assertThat(localState.planets).isEqualTo(mockPlanets)

            cancelAndIgnoreRemainingEvents()
        }

        verify(planetsRepository).getPlanets()
        verify(planetsRepository).getLocalPlanets()
    }

    @Test
    fun `fetchPlanets should show error when local call fails`() = runTest {

        whenever(planetsRepository.getPlanets()).thenReturn(Result.Success(mockPlanets))
        whenever(planetsRepository.getLocalPlanets()).thenReturn(
            flow { emit(Result.Error(DataError.LocalDataError.NOT_FOUND)) }
        )

        viewModel = PlanetListViewModel(planetsRepository)

        viewModel.state.test(timeout = 3.seconds) {
            val remoteState = expectMostRecentItem()
            assertThat(remoteState.isLoading).isFalse()
            assertThat(remoteState.error).isNull()
            assertThat(remoteState.planets).isEqualTo(mockPlanets)

            val localState = awaitItem()
            assertThat(localState.isLoading).isFalse()
            assertThat(localState.error).isNotNull()
            assertThat(localState.planets).isNotEmpty()

            cancelAndIgnoreRemainingEvents()
        }
        verify(planetsRepository).getPlanets()
        verify(planetsRepository).getLocalPlanets()
    }

    @Test
    fun `fetchPlanets should show error when both remote and local calls fail`() = runTest {

        whenever(planetsRepository.getPlanets()).thenReturn(Result.Error(DataError.NetworkError.UNKNOWN))
        whenever(planetsRepository.getLocalPlanets()).thenReturn(
            flow { emit(Result.Error(DataError.LocalDataError.NOT_FOUND)) }
        )

        viewModel = PlanetListViewModel(planetsRepository)

        viewModel.state.test(timeout = 3.seconds) {
            val remoteState = awaitItem()
            assertThat(remoteState.isLoading).isFalse()
            assertThat(remoteState.error).isNotNull()
            assertThat(remoteState.planets).isEmpty()

            val localState = awaitItem()
            assertThat(localState.isLoading).isFalse()
            assertThat(localState.error).isNotNull()
            assertThat(localState.planets).isEmpty()

            cancelAndConsumeRemainingEvents()
        }

        verify(planetsRepository).getPlanets()
        verify(planetsRepository).getLocalPlanets()
    }

    @Test
    fun `local planets should update when data changes`() = runTest {

        val updatedPlanets = listOf(
            Planet(
                name = "Hoth",
                climate = "frozen",
                orbitalPeriod = "549",
                gravity = "1.1 standard",
                image = "hoth.jpg"
            )
        )

        whenever(planetsRepository.getPlanets()).thenReturn(Result.Success(mockPlanets))
        whenever(planetsRepository.getLocalPlanets()).thenReturn(
            flow {
                emit(Result.Success(mockPlanets))
                emit(Result.Success(updatedPlanets))
            }
        )

        viewModel = PlanetListViewModel(planetsRepository)

        viewModel.state.test(timeout = 3.seconds) {
            val firstLocalState = awaitItem()
            assertThat(firstLocalState.planets).isEqualTo(mockPlanets)
            assertThat(firstLocalState.planets).containsExactly(mockPlanets[0], mockPlanets[1])

            val secondLocalState = awaitItem()
            assertThat(secondLocalState.planets).isEqualTo(updatedPlanets)
            assertThat(secondLocalState.planets).hasSize(1)
            assertThat(secondLocalState.planets[0].name).isEqualTo("Hoth")
            assertThat(secondLocalState.planets[0].climate).isEqualTo("frozen")

            cancelAndIgnoreRemainingEvents()
        }

        verify(planetsRepository).getPlanets()
        verify(planetsRepository).getLocalPlanets()
    }

}
