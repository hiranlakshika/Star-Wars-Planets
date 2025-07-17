package com.sysco.planetdetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sysco.planetdetails.R
import com.sysco.planetdetails.domain.repository.PlanetDetailsRepository
import com.sysco.shared.core.domain.model.Result
import com.sysco.shared.core.presentation.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetDetailsViewModel @Inject constructor(
    private val planetDetailsRepository: PlanetDetailsRepository
) :
    ViewModel() {

    private val _state = MutableStateFlow(PlanetDetailsState())

    val state: StateFlow<PlanetDetailsState> get() = _state


    fun onEvent(event: PlanetDetailsEvent) {
        when (event) {
            is PlanetDetailsEvent.OnInit -> {
                fetchPlanetDetails(event.planetName)
            }
        }
    }


    private fun fetchPlanetDetails(planetName: String) = viewModelScope.launch(Dispatchers.IO) {
        planetDetailsRepository.getLocalPlanetByName(planetName).collectLatest { result ->
            when (result) {
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            error = UiText.StringResource(R.string.no_data_found)
                        )
                    }
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            planet = result.data
                        )
                    }
                }
            }

        }
    }
}