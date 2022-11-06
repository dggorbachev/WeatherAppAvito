package com.dggorbachev.weatherapp.data.remote_list_weather.model

import com.google.gson.annotations.SerializedName

data class WeekWeatherDescResponseModel(
    @SerializedName("description")
    val description: String,
)
