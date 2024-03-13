package com.ciutacuclaudia.data.remote.model.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Properties(
    @Json(name = "@id")
    val id1: String,
    @Json(name = "@type")
    val type: String,
    val id: String,
    val areaDesc: String,
    val geocode: Geocode,
    val affectedZones: List<String>,
    val references: List<Any>,
    val sent: String,
    val effective: String,
    val onset: String,
    val expires: String,
    val ends: String?,
    val status: String,
    val messageType: String,
    val category: String,
    val severity: String,
    val certainty: String,
    val urgency: String,
    val event: String,
    val sender: String,
    val senderName: String,
    val headline: String,
    val description: String,
    val instruction: String?,
    val response: String,
    val parameters: Parameters
)