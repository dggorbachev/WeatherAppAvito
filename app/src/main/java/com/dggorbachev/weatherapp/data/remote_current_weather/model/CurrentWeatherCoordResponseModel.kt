package com.dggorbachev.weatherapp.data.remote_current_weather.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherCoordResponseModel(
    @SerializedName("lon")
    val longitude: Double,
    @SerializedName("lat")
    val latitude: Double,
)