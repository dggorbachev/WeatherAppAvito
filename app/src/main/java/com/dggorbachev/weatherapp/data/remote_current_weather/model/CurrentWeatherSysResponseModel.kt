package com.dggorbachev.weatherapp.data.remote_current_weather.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherSysResponseModel(
    @SerializedName("sunset")
    val sunset: Long,
)
