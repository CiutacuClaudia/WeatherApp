package com.ciutacuclaudia.domain

import com.ciutacuclaudia.domain.model.result.Result
import kotlinx.coroutines.flow.Flow

interface WeatherUseCase {
    fun getWeatherAlerts(): Flow<Result>
}