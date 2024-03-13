package com.ciutacuclaudia.domain

import com.ciutacuclaudia.data.remote.model.result.ErrorCauseEntity
import com.ciutacuclaudia.data.remote.model.result.ResultEntity
import com.ciutacuclaudia.data.remote.model.weather.WeatherResponse
import com.ciutacuclaudia.data.remote.repository.WeatherRepository
import com.ciutacuclaudia.domain.model.WeatherAlert
import com.ciutacuclaudia.domain.model.result.ErrorCause
import com.ciutacuclaudia.domain.model.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : WeatherUseCase {

    override fun getWeatherAlerts(): Flow<Result> = flow {
        weatherRepository.getWeatherAlerts().collect { resultEntity ->
            when (resultEntity) {
                is ResultEntity.Success<*> -> {
                    val weatherResponse = resultEntity.payload as WeatherResponse

                    val weatherAlerts = weatherResponse.features.map { feature ->
                        val properties = feature.properties
                        WeatherAlert(
                            event = properties.event,
                            startDate = properties.effective,
                            endDate = properties.ends,
                            source = properties.senderName
                        )
                    }

                    emit(Result.Success(weatherAlerts))
                }

                is ResultEntity.Error -> {
                    when (resultEntity.errorCauseEntity) {
                        ErrorCauseEntity.EMPTY_LIST -> {
                            emit(Result.Error(ErrorCause.EMPTY_LIST))
                        }

                        ErrorCauseEntity.UNKNOWN_ERROR -> {
                            emit(Result.Error(ErrorCause.UNKNOWN_ERROR))
                        }
                    }
                }
            }
        }
    }
}