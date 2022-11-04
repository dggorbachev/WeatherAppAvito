package com.dggorbachev.weatherapp.data.local_location

import com.dggorbachev.weatherapp.base.SingleLiveEvent

interface LocalLocationRepo {
    val isLocationAllowed: SingleLiveEvent<Boolean>

    suspend fun locationEnableChange(isEnabled: Boolean)

    suspend fun getCoordinates(): Pair<Double, Double>

    suspend fun setCoordinates(latitude: Double, longitude: Double)
}