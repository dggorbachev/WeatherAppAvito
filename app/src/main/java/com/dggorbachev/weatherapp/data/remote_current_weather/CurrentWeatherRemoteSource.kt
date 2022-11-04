package com.dggorbachev.weatherapp.data.remote_current_weather

import com.dggorbachev.weatherapp.data.remote_current_weather.model.CurrentWeatherResponseModel
import javax.inject.Inject

class CurrentWeatherRemoteSource @Inject constructor(private val currentWeatherApi: CurrentWeatherApi) {

    suspend fun get(lat: Double, lon: Double): CurrentWeatherResponseModel =
        currentWeatherApi.get(lat = lat, lon = lon)
}