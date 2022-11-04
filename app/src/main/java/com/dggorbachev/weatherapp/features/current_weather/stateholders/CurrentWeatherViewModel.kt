package com.dggorbachev.weatherapp.features.current_weather.stateholders

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dggorbachev.weatherapp.base.SingleLiveEvent
import com.dggorbachev.weatherapp.data.local_location.LocalLocationRepo
import com.dggorbachev.weatherapp.data.remote_current_weather.RemoteCurrentWeatherRepo
import com.dggorbachev.weatherapp.domain.AsyncState
import com.dggorbachev.weatherapp.domain.model.CurrentWeather
import com.dggorbachev.weatherapp.features.preferences_manager.PreferencesManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CurrentWeatherViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val currentWeatherRepo: RemoteCurrentWeatherRepo,
    private val localLocationRepo: LocalLocationRepo,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    val isLocationAllowed = localLocationRepo.isLocationAllowed

    val coordinates = SingleLiveEvent<Pair<Double, Double>>()

    val currentWeather: SingleLiveEvent<AsyncState<CurrentWeather>> =
        currentWeatherRepo.weatherState

    val savedLocation = SingleLiveEvent<String>()
    val isRegionPicked = SingleLiveEvent<Boolean>()

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): CurrentWeatherViewModel
    }

    fun getCoordinates() {
        viewModelScope.launch {
            coordinates.postValue(localLocationRepo.getCoordinates())
        }
    }

    fun getCurrentWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            currentWeatherRepo.get(lat, lon)
        }
    }

    fun getSavedLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            savedLocation.postValue(preferencesManager.regionPreferencesFlow.first().region)
            isRegionPicked.postValue(preferencesManager.systemPreferencesFlow.first().isRegionPicked)
        }
    }
}