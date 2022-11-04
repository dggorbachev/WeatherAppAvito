package com.dggorbachev.weatherapp.data.remote_list_weather.model

import com.google.gson.annotations.SerializedName

// date - seconds
data class WeatherResponseModel(
    @SerializedName("dt")
    val date: Long,
    @SerializedName("sunset")
    val sunset: Long,
//    @SerializedName("temp")
//    val temp: TemperatureResponseModel,
)