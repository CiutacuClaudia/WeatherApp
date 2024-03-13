package com.ciutacuclaudia.data.remote.repository

import com.ciutacuclaudia.data.remote.model.result.ResultEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeatherAlerts(): Flow<ResultEntity>
}