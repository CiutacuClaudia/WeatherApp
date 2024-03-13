package com.ciutacuclaudia.data.remote.model.weather

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Geometry(
    val type: String,
    val coordinates: List<List<List<Double>>>
)