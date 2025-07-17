package com.sysco.planetdetails.presentation

import androidx.lifecycle.ViewModel
import com.sysco.shared.core.domain.repository.PlanetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlanetDetailsViewModel @Inject constructor(private val planetsRepository: PlanetsRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(PlanetDetailsState())

    val state: StateFlow<PlanetDetailsState> get() = _state
}