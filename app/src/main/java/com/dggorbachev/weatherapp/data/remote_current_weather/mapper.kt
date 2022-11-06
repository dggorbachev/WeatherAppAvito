package com.dggorbachev.weatherapp.data.remote_current_weather

import com.dggorbachev.weatherapp.data.remote_current_weather.model.CurrentWeatherResponseModel
import com.dggorbachev.weatherapp.domain.model.Weather

object RemoteCurrentWeatherMapper {

    fun CurrentWeatherResponseModel.toDomainModel() =
        Weather(
            temp = this.main.temp,
            feelsLike = this.main.feelsLike,
            tempMin = this.main.tempMin,
            tempMax = this.main.tempMax,
            humidity = this.main.humidity,
            windSpeed = this.wind.speed,
            description = this.weather[0].description,
            sunset = this.sys.sunset,
            longitude = this.coord.longitude,
            latitude = this.coord.latitude,
        )
}