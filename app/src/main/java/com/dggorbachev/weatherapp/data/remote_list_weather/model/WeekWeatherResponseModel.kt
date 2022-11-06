package com.dggorbachev.weatherapp.data.remote_list_weather.model

import com.google.gson.annotations.SerializedName

// date - seconds
data class WeekWeatherResponseModel(
    @SerializedName("dt")
    val date: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("temp")
    val temp: WeekWeatherTempResponseModel,
    @SerializedName("feels_like")
    val feelsLike: WeekWeatherFeelsResponseModel,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("weather")
    val weatherDesc: List<WeekWeatherDescResponseModel>,
)