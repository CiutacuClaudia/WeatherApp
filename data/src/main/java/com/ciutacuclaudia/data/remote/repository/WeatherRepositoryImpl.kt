package com.ciutacuclaudia.data.remote.repository

import com.ciutacuclaudia.data.remote.api.WeatherAlertAPI
import com.ciutacuclaudia.data.remote.model.result.ErrorCauseEntity
import com.ciutacuclaudia.data.remote.model.result.ResultEntity
import com.ciutacuclaudia.data.remote.model.weather.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherAlertAPI: WeatherAlertAPI
) : WeatherRepository {

    override fun getWeatherAlerts(): Flow<ResultEntity> = flow {
        try {
            val result = weatherAlertAPI.getActiveAlerts()
            when {
                result.isSuccessful -> {
                    if (result.body() != null) {
                        emit(ResultEntity.Success(result.body() as WeatherResponse))
                    } else {
                        emit(ResultEntity.Error(ErrorCauseEntity.EMPTY_LIST))
                    }
                }

                else -> {
                    emit(ResultEntity.Error(ErrorCauseEntity.UNKNOWN_ERROR))
                }
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(ResultEntity.Error(ErrorCauseEntity.UNKNOWN_ERROR))
        }
    }
}