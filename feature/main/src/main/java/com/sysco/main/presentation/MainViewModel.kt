package com.sysco.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(MainState())

    val state: StateFlow<MainState> get() = _state

    init {
        simulateSplashScreen()
    }

    private fun simulateSplashScreen() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        delay(2000L)
        _state.update {
            it.copy(
                isLoading = false
            )
        }
    }
}