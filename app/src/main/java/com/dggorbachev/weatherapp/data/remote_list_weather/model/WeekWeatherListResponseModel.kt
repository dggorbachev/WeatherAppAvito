package com.dggorbachev.weatherapp.data.remote_list_weather.model

import com.google.gson.annotations.SerializedName

data class WeekWeatherListResponseModel(
    @SerializedName("lon")
    val longitude: Double,
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("daily")
    val weatherList: List<WeekWeatherResponseModel>,
)