package com.dggorbachev.weatherapp.data.remote_current_weather

import com.dggorbachev.weatherapp.domain.model.CurrentWeather

interface RemoteCurrentWeatherRepo {
    suspend fun get(lat: Double, lon: Double): CurrentWeather
}