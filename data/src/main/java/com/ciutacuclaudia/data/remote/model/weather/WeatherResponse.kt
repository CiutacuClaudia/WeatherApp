package com.ciutacuclaudia.data.remote.model.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @Json(name = "@context")
    val context: List<Any>,
    val type: String,
    val features: List<Feature>,
    val title: String,
    val updated: String
)