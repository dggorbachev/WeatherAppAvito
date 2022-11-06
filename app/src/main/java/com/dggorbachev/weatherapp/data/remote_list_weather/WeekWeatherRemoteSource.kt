package com.dggorbachev.weatherapp.data.remote_list_weather

import com.dggorbachev.weatherapp.data.remote_current_weather.model.CurrentWeatherResponseModel
import com.dggorbachev.weatherapp.data.remote_list_weather.model.WeekWeatherListResponseModel
import javax.inject.Inject

class WeekWeatherRemoteSource @Inject constructor(private val weekWeatherApi: WeekWeatherApi) {

    suspend fun get(lat: Double, lon: Double): WeekWeatherListResponseModel =
        weekWeatherApi.get(lat = lat, lon = lon)
}