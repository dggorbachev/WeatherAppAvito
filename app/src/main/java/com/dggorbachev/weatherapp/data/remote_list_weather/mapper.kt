package com.dggorbachev.weatherapp.data.remote_list_weather

import com.dggorbachev.weatherapp.data.remote_list_weather.model.WeekWeatherListResponseModel
import com.dggorbachev.weatherapp.domain.model.WeekWeather
import java.util.*

object RemoteWeekWeatherMapper {

    fun WeekWeatherListResponseModel.toDomainModel(): List<WeekWeather> {
        val resList = mutableListOf<WeekWeather>()

        for (item in this.weatherList) {
            resList.add(
                WeekWeather(
                    id = UUID.randomUUID(),
                    date = item.date,
                    feelsLike = item.feelsLike.day,
                    tempMin = item.temp.min,
                    tempMax = item.temp.max,
                    humidity = item.humidity,
                    windSpeed = item.windSpeed,
                    description = item.weatherDesc[0].description,
                    sunrise = item.sunrise,
                    sunset = item.sunset,
                    longitude = this.longitude,
                    latitude = this.latitude))
        }

        return resList
    }
}