package com.dggorbachev.weatherapp.data.remote_current_weather

import com.dggorbachev.weatherapp.base.SingleLiveEvent
import com.dggorbachev.weatherapp.data.remote_current_weather.RemoteCurrentWeatherMapper.toDomainModel
import com.dggorbachev.weatherapp.domain.AsyncState
import com.dggorbachev.weatherapp.domain.model.CurrentWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteCurrentWeatherRepoImpl @Inject constructor(
    private val source: CurrentWeatherRemoteSource,
) : RemoteCurrentWeatherRepo {

    override val weatherState: SingleLiveEvent<AsyncState<CurrentWeather>>
        get() = _weatherState

    private val _weatherState = SingleLiveEvent<AsyncState<CurrentWeather>>()

    override suspend fun get(lat: Double, lon: Double) {
        withContext(Dispatchers.IO) {
            weatherState.postValue(AsyncState.Loading)
            try {
                weatherState.postValue(AsyncState.Loaded(source.get(lat, lon).toDomainModel()))
            } catch (e: java.net.SocketTimeoutException) {
                weatherState.postValue(AsyncState.Error("Не удалось загрузить данные"))
            } catch (e: Exception) {
                weatherState.postValue(AsyncState.Error("Что-то пошло не так..."))
            }
        }
    }
}