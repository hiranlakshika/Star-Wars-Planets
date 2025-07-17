package com.sysco.planets.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sysco.planets.R
import com.sysco.planets.domain.repository.PlanetsRepository
import com.sysco.shared.core.domain.model.Result
import com.sysco.shared.core.presentation.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetListViewModel @Inject constructor(private val planetsRepository: PlanetsRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(PlanetsState())

    val state: StateFlow<PlanetsState> get() = _state


    fun onEvent(event: PlanetsEvent) {
        when (event) {
            PlanetsEvent.OnLoad -> {
                fetchPlanets()
            }

            else -> Unit
        }
    }

    private fun fetchPlanets() = viewModelScope.launch(Dispatchers.IO) {
        _state.update { it.copy(isLoading = true) }

        when (val result = planetsRepository.getPlanets()) {
            is Result.Error -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = UiText.StringResource(R.string.failed_to_load_data)
                    )
                }
            }

            is Result.Success -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        planets = result.data
                    )
                }
            }
        }
    }

}