@file:Suppress("DEPRECATION")

package com.ciutacuclaudia.data

import com.ciutacuclaudia.data.remote.api.WeatherAlertAPI
import com.ciutacuclaudia.data.remote.model.result.ErrorCauseEntity
import com.ciutacuclaudia.data.remote.model.result.ResultEntity
import com.ciutacuclaudia.data.remote.model.weather.WeatherResponse
import com.ciutacuclaudia.data.remote.repository.WeatherRepository
import com.ciutacuclaudia.data.remote.repository.WeatherRepositoryImpl
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

class RepositoryTest {

    @Mock
    lateinit var weatherApi: WeatherAlertAPI
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        weatherRepository = WeatherRepositoryImpl(weatherApi)
    }

    @Test
    fun testGetActiveAlerts_Success() = runBlocking {
        val responseBody = WeatherResponse(
            emptyList(), "testType", emptyList(),
            "Test Title", "2024-03-18"
        )
        val successResponse = Response.success(responseBody)

        `when`(weatherApi.getActiveAlerts()).thenReturn(successResponse)

        weatherRepository.getWeatherAlerts().collect {
            assertEquals(it, ResultEntity.Success(responseBody))
        }
    }

    @Test
    fun testGetActiveAlerts_ErrorEmptyList() = runBlocking {
        val emptyListResponse = Response.success<WeatherResponse>(null)

        `when`(weatherApi.getActiveAlerts()).thenReturn(emptyListResponse)

        weatherRepository.getWeatherAlerts().collect {
            assertEquals(it, ResultEntity.Error(ErrorCauseEntity.EMPTY_LIST))
        }
    }


    @Test
    fun testGetActiveAlerts_ErrorUnknown() = runBlocking {

        val errorResponse = Response.error<WeatherResponse>(500, "No internet".toResponseBody())

        `when`(weatherApi.getActiveAlerts()).thenThrow(HttpException(errorResponse))

        weatherRepository.getWeatherAlerts().collect {
            assertEquals(it, ResultEntity.Error(ErrorCauseEntity.UNKNOWN_ERROR))
        }
    }
}