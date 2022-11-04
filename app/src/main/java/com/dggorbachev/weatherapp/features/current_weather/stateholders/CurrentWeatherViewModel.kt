package com.dggorbachev.weatherapp.features.current_weather.stateholders

import androidx.lifecycle.*
import com.dggorbachev.weatherapp.data.remote_current_weather.RemoteCurrentWeatherRepo
import com.dggorbachev.weatherapp.domain.model.CurrentWeather
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class CurrentWeatherViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val currentWeatherRepo: RemoteCurrentWeatherRepo,
) : ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): CurrentWeatherViewModel
    }

    fun getCurrentWeather(lat: Double, lon: Double): LiveData<CurrentWeather> {
        val result = MutableLiveData<CurrentWeather>()
        viewModelScope.launch {
            result.postValue(currentWeatherRepo.get(lat, lon))
        }
        return result
    }
}