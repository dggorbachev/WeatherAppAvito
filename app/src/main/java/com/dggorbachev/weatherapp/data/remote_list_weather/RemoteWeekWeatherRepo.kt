package com.dggorbachev.weatherapp.data.remote_list_weather

import com.dggorbachev.weatherapp.base.SingleLiveEvent
import com.dggorbachev.weatherapp.domain.AsyncState
import com.dggorbachev.weatherapp.domain.model.Weather
import com.dggorbachev.weatherapp.domain.model.WeekWeather

interface RemoteWeekWeatherRepo {
    val weatherState: SingleLiveEvent<AsyncState<List<WeekWeather>>>

    suspend fun get(lat: Double, lon: Double)
}