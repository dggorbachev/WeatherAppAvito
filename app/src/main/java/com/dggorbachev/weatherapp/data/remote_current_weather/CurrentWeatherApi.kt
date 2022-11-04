package com.dggorbachev.weatherapp.data.remote_current_weather

import com.dggorbachev.weatherapp.base.common.Constants.API_KEY
import com.dggorbachev.weatherapp.data.remote_current_weather.model.CurrentWeatherResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherApi {

    @GET("data/2.5/weather")
    suspend fun get(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru",
        @Query("appid") appid: String = API_KEY,
    ): CurrentWeatherResponseModel
}