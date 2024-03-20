package com.ciutacuclaudia.data.remote.model.weather

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Geocode(
    val SAME: List<String>?,
    val UGC: List<String>
)