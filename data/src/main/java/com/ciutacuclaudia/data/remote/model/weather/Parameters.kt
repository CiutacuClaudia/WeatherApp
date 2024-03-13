package com.ciutacuclaudia.data.remote.model.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Parameters(
    @Json(name = "AWIPSidentifier")
    val AWIPSidentifier: List<String>,
    val WMOidentifier: List<String>,
    val NWSheadline: List<String>?,
    val BLOCKCHANNEL: List<String>,
    @Json(name = "EAS-ORG")
    val EASORG: List<String>?,
    val VTEC: List<String>?,
    val expiredReferences: List<String>?,
    val eventEndingTime: List<String>?
)