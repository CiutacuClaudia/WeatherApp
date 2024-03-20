package com.ciutacuclaudia.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ciutacuclaudia.domain.WeatherUseCase
import com.ciutacuclaudia.domain.model.WeatherAlert
import com.ciutacuclaudia.domain.model.result.ErrorCause
import com.ciutacuclaudia.domain.model.result.Result
import com.ciutacuclaudia.weatherapp.mock.MainDispatcherRule
import com.ciutacuclaudia.weatherapp.viewmodel.MainViewModel
import com.ciutacuclaudia.weatherapp.viewmodel.ScreenState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var weatherUseCase: WeatherUseCase

    private lateinit var mainViewModel: MainViewModel

    private lateinit var weatherAlert: WeatherAlert

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        weatherAlert = WeatherAlert("1", "2", "3", "4")
        mainViewModel = MainViewModel(weatherUseCase)
    }

    @Test
    fun getWeatherAlerts_Success() = runBlocking {

        `when`(weatherUseCase.getWeatherAlerts()).thenReturn(
            flow {
                emit(Result.Success(listOf(weatherAlert)))
            }
        )
        mainViewModel.getWeatherAlerts()
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()

        val uiState = ScreenState.Success(arrayListOf(weatherAlert))
        assert(uiState == mainViewModel.uiState.value)

    }

    @Test
    fun getWeatherAlerts_Loading() = runBlocking {
        mainViewModel.getWeatherAlerts()
        assert(mainViewModel.uiState.value == ScreenState.Loading)
    }

    @Test
    fun getWeatherAlerts_PayloadError() = runBlocking {
        `when`(weatherUseCase.getWeatherAlerts()).thenReturn(
            flow {
                emit(Result.Success(listOf(weatherAlert).toSet()))
            }
        )
        mainViewModel.getWeatherAlerts()
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()

        val uiState = ScreenState.Error(ErrorCause.UNKNOWN_ERROR)
        assert(uiState == mainViewModel.uiState.value)
    }

    @Test
    fun getWeatherAlerts_ResultErrorUnknown() = runBlocking {
        `when`(weatherUseCase.getWeatherAlerts()).thenReturn(
            flow {
                emit(Result.Error(ErrorCause.UNKNOWN_ERROR))
            }
        )
        mainViewModel.getWeatherAlerts()
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()

        val uiState = ScreenState.Error(ErrorCause.UNKNOWN_ERROR)
        assert(uiState == mainViewModel.uiState.value)
    }

    @Test
    fun getWeatherAlerts_ResultError() = runBlocking {
        `when`(weatherUseCase.getWeatherAlerts()).thenReturn(
            flow {
                emit(Result.Error(ErrorCause.EMPTY_LIST))
            }
        )
        mainViewModel.getWeatherAlerts()
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()

        val uiState = ScreenState.Error(ErrorCause.EMPTY_LIST)
        assert(uiState == mainViewModel.uiState.value)
    }
}