package com.ciutacuclaudia.domain.model

data class WeatherAlert(
    val event: String,
    val startDate: String,
    val endDate: String?,
    val source: String
)