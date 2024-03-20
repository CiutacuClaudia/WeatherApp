package com.ciutacuclaudia.domain

import com.ciutacuclaudia.data.remote.model.result.ErrorCauseEntity
import com.ciutacuclaudia.data.remote.model.result.ResultEntity
import com.ciutacuclaudia.data.remote.model.weather.WeatherResponse
import com.ciutacuclaudia.data.remote.repository.WeatherRepository
import com.ciutacuclaudia.domain.model.WeatherAlert
import com.ciutacuclaudia.domain.model.result.ErrorCause
import com.ciutacuclaudia.domain.model.result.Result
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class UseCaseTest {

    @Mock
    lateinit var weatherRepository: WeatherRepository
    private lateinit var weatherUseCase: WeatherUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        weatherUseCase = WeatherUseCaseImpl(weatherRepository)
    }

    @Test
    fun getWeatherAlerts_Success() = runBlocking {
        val weatherResponse = WeatherResponse(
            emptyList(), "type", emptyList(), "title", "asd"
        )

        `when`(weatherRepository.getWeatherAlerts()).thenReturn(
            flow {
                emit(ResultEntity.Success(weatherResponse))
            }
        )

        val weatherAlerts = weatherResponse.features.map { feature ->
            val properties = feature.properties
            WeatherAlert(
                event = properties.event,
                startDate = properties.effective,
                endDate = properties.ends,
                source = properties.senderName
            )
        }

        weatherUseCase.getWeatherAlerts().collect { result ->
            assert(result is Result.Success<*>)
            assert((result as Result.Success<*>).payload == weatherAlerts)
        }
    }

    @Test
    fun getWeatherAlerts_ErrorEmptyList() = runBlocking {
        `when`(weatherRepository.getWeatherAlerts()).thenReturn(
            flow {
                emit(ResultEntity.Error(ErrorCauseEntity.EMPTY_LIST))
            }
        )

        weatherUseCase.getWeatherAlerts().collect { result ->
            assert(result is Result.Error)
            assert((result as Result.Error).errorCause == ErrorCause.EMPTY_LIST)
        }
    }

    @Test
    fun getWeatherAlerts_ErrorUnknown() = runBlocking {
        `when`(weatherRepository.getWeatherAlerts()).thenReturn(
            flow {
                emit(ResultEntity.Error(ErrorCauseEntity.UNKNOWN_ERROR))
            }
        )

        weatherUseCase.getWeatherAlerts().collect { result ->
            assert(result is Result.Error)
            assert((result as Result.Error).errorCause == ErrorCause.UNKNOWN_ERROR)
        }
    }
}