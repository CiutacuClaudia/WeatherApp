package com.ciutacuclaudia.data.remote.api

import com.ciutacuclaudia.data.remote.model.weather.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET

interface WeatherAlertAPI {
    @GET("alerts/active?status=actual&message_type=alert")
    suspend fun getActiveAlerts(): Response<WeatherResponse>
}