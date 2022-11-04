package com.dggorbachev.weatherapp.di.modules.data

import com.dggorbachev.weatherapp.data.remote_current_weather.CurrentWeatherApi
import com.dggorbachev.weatherapp.data.remote_current_weather.CurrentWeatherRemoteSource
import com.dggorbachev.weatherapp.data.remote_current_weather.RemoteCurrentWeatherRepo
import com.dggorbachev.weatherapp.data.remote_current_weather.RemoteCurrentWeatherRepoImpl
import com.dggorbachev.weatherapp.di.annotations.AppScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object RemoteDataModule {

    @Provides
    @AppScope
    fun providePlacesApi(retrofit: Retrofit): CurrentWeatherApi =
        retrofit.create(CurrentWeatherApi::class.java)

    @Provides
    @AppScope
    fun provideCurrentWeatherRemoteSource(weatherApi: CurrentWeatherApi): CurrentWeatherRemoteSource =
        CurrentWeatherRemoteSource(weatherApi)

    @Provides
    @AppScope
    fun provideRemoteCurrentWeatherRepo(currentWeatherRemoteSource: CurrentWeatherRemoteSource): RemoteCurrentWeatherRepo =
        RemoteCurrentWeatherRepoImpl(currentWeatherRemoteSource)
}