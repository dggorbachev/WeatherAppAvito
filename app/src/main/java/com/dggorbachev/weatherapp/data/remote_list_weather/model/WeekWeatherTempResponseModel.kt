package com.dggorbachev.weatherapp.data.remote_list_weather.model

import com.google.gson.annotations.SerializedName

data class WeekWeatherTempResponseModel(
    @SerializedName("min")
    val min: Double,
    @SerializedName("max")
    val max: Double,
)
