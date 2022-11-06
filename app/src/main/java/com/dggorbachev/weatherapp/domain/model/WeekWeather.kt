package com.dggorbachev.weatherapp.domain.model

import java.util.*

data class WeekWeather(
    val id: UUID,
    val date: Long,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Int,
    val windSpeed: Double,
    val description: String,
    val sunrise: Long,
    val sunset: Long,
    val longitude: Double,
    val latitude: Double,
)
