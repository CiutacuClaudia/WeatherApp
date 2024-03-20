package com.ciutacuclaudia.domain.model

data class WeatherAlert(
    val event: String,
    val startDate: String,
    val endDate: String?,
    val source: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeatherAlert

        if (event != other.event) return false
        if (startDate != other.startDate) return false
        if (endDate != other.endDate) return false
        if (source != other.source) return false

        return true
    }

    override fun hashCode(): Int {
        var result = event.hashCode()
        result = 31 * result + startDate.hashCode()
        result = 31 * result + (endDate?.hashCode() ?: 0)
        result = 31 * result + source.hashCode()
        return result
    }
}