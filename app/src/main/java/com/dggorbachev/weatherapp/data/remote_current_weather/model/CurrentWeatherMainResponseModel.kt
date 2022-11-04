package com.dggorbachev.weatherapp.data.remote_current_weather.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherMainResponseModel(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("humidity")
    val humidity: Int,
)