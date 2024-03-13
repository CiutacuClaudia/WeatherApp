package com.ciutacuclaudia.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ciutacuclaudia.domain.WeatherUseCase
import com.ciutacuclaudia.domain.model.WeatherAlert
import com.ciutacuclaudia.domain.model.result.ErrorCause
import com.ciutacuclaudia.domain.model.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

sealed class ScreenState {
    data object Loading : ScreenState()
    data class Error(val errorCause: ErrorCause) : ScreenState()
    data class Success(val weatherAlerts: List<WeatherAlert>) : ScreenState()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    val randomValueForSession = Random.nextInt()

    private val _uiState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getWeatherAlerts()
    }

    fun getWeatherAlerts() {
        _uiState.tryEmit(ScreenState.Loading)

        viewModelScope.launch {
            weatherUseCase.getWeatherAlerts().collect { result ->
                when (result) {
                    is Result.Success<*> -> {
                        val payload = result.payload
                        if (payload is List<*>) {
                            _uiState.tryEmit(
                                ScreenState.Success(
                                    weatherAlerts = payload.filterIsInstance<WeatherAlert>()
                                )
                            )
                        } else {
                            _uiState.tryEmit(ScreenState.Error(errorCause = ErrorCause.UNKNOWN_ERROR))
                        }
                    }

                    else -> {
                        _uiState.tryEmit(ScreenState.Error((result as Result.Error).errorCause))
                    }
                }
            }
        }
    }
}