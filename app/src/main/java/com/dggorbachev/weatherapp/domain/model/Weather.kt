package com.dggorbachev.weatherapp.domain.model

data class Weather(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Int,
    val windSpeed: Double,
    val description: String,
    val sunset: Long,
    val longitude: Double,
    val latitude: Double,
)
