package com.dggorbachev.weatherapp.data.remote_current_weather.model

import com.google.gson.annotations.SerializedName

// https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&units=metric&lang=ru&appid=1384a7fcd9efed7440433a9d3c4fd1f1

data class CurrentWeatherResponseModel(
    @SerializedName("coord")
    val coord: CurrentWeatherCoordResponseModel,
    @SerializedName("main")
    val main: CurrentWeatherMainResponseModel,
    @SerializedName("wind")
    val wind: CurrentWeatherWindResponseModel,
    @SerializedName("weather")
    val weather: List<CurrentWeatherDescResponseModel>,
    @SerializedName("sys")
    val sys: CurrentWeatherSysResponseModel,
)