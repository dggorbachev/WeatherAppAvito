package com.dggorbachev.weatherapp.data.remote_list_weather

import com.dggorbachev.weatherapp.base.common.Constants.API_KEY
import com.dggorbachev.weatherapp.data.remote_current_weather.model.CurrentWeatherResponseModel
import com.dggorbachev.weatherapp.data.remote_list_weather.model.WeekWeatherListResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeekWeatherApi {

    @GET("data/2.5/onecall")
    suspend fun get(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "current,hourly,minutely,alerts",
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru",
        @Query("appid") appid: String = API_KEY,
    ): WeekWeatherListResponseModel
}