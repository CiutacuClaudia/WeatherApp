package com.ciutacuclaudia.data.remote.model.weather

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Feature(
    val id: String,
    val type: String,
    val geometry: Geometry?,
    val properties: Properties
)