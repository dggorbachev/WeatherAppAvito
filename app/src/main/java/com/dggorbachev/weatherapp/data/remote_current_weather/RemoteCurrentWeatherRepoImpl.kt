package com.dggorbachev.weatherapp.data.remote_current_weather

import com.dggorbachev.weatherapp.data.remote_current_weather.RemoteCurrentWeatherMapper.toDomainModel
import com.dggorbachev.weatherapp.domain.model.CurrentWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteCurrentWeatherRepoImpl @Inject constructor(
    private val source: CurrentWeatherRemoteSource,
) : RemoteCurrentWeatherRepo {
    override suspend fun get(lat: Double, lon: Double): CurrentWeather =
        withContext(Dispatchers.IO) {
            source.get(lat, lon).toDomainModel()
        }

}