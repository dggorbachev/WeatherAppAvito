package com.dggorbachev.weatherapp.data.remote_current_weather

import com.dggorbachev.weatherapp.base.SingleLiveEvent
import com.dggorbachev.weatherapp.domain.AsyncState
import com.dggorbachev.weatherapp.domain.model.CurrentWeather

interface RemoteCurrentWeatherRepo {
    val weatherState: SingleLiveEvent<AsyncState<CurrentWeather>>

    suspend fun get(lat: Double, lon: Double)
}