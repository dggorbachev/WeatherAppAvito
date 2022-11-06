package com.dggorbachev.weatherapp.data.remote_list_weather

import com.dggorbachev.weatherapp.base.SingleLiveEvent
import com.dggorbachev.weatherapp.data.remote_list_weather.RemoteWeekWeatherMapper.toDomainModel
import com.dggorbachev.weatherapp.domain.AsyncState
import com.dggorbachev.weatherapp.domain.model.WeekWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteWeekWeatherRepoImpl @Inject constructor(
    private val source: WeekWeatherRemoteSource,
) : RemoteWeekWeatherRepo {

    override val weatherState: SingleLiveEvent<AsyncState<List<WeekWeather>>>
        get() = _weatherState

    private val _weatherState = SingleLiveEvent<AsyncState<List<WeekWeather>>>()

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