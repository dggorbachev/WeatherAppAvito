package com.dggorbachev.weatherapp.data.local_location

import com.dggorbachev.weatherapp.base.SingleLiveEvent
import com.dggorbachev.weatherapp.base.common.Constants.BASE_LATITUDE
import com.dggorbachev.weatherapp.base.common.Constants.BASE_LONGITUDE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalLocationRepoImpl @Inject constructor() : LocalLocationRepo {

    override val isLocationAllowed: SingleLiveEvent<Boolean>
        get() = mutableState

    private val mutableState = SingleLiveEvent<Boolean>()

    private var coordinates: Pair<Double, Double> = Pair(BASE_LATITUDE, BASE_LONGITUDE)

    override suspend fun locationEnableChange(isEnabled: Boolean) = withContext(Dispatchers.IO) {
        mutableState.postValue(isEnabled)
    }

    override suspend fun getCoordinates(): Pair<Double, Double> =
        withContext(Dispatchers.IO) { coordinates }

    override suspend fun setCoordinates(latitude: Double, longitude: Double) =
        withContext(Dispatchers.IO) {
            coordinates = Pair(latitude, longitude)
        }
}