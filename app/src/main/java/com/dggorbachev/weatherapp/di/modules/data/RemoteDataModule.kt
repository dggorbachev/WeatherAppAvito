package com.dggorbachev.weatherapp.di.modules.data

import com.dggorbachev.weatherapp.data.remote_current_weather.CurrentWeatherApi
import com.dggorbachev.weatherapp.data.remote_current_weather.CurrentWeatherRemoteSource
import com.dggorbachev.weatherapp.data.remote_current_weather.RemoteCurrentWeatherRepo
import com.dggorbachev.weatherapp.data.remote_current_weather.RemoteCurrentWeatherRepoImpl
import com.dggorbachev.weatherapp.data.remote_list_weather.RemoteWeekWeatherRepo
import com.dggorbachev.weatherapp.data.remote_list_weather.RemoteWeekWeatherRepoImpl
import com.dggorbachev.weatherapp.data.remote_list_weather.WeekWeatherApi
import com.dggorbachev.weatherapp.data.remote_list_weather.WeekWeatherRemoteSource
import com.dggorbachev.weatherapp.data.remote_search_hints.RemoteSearchHintsRepo
import com.dggorbachev.weatherapp.data.remote_search_hints.RemoteSearchHintsRepoImpl
import com.dggorbachev.weatherapp.data.remote_search_hints.SearchHintsApi
import com.dggorbachev.weatherapp.data.remote_search_hints.SearchHintsRemoteSource
import com.dggorbachev.weatherapp.di.annotations.AppScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object RemoteDataModule {

    @Provides
    @AppScope
    fun provideCurrentWeatherApi(retrofit: Retrofit): CurrentWeatherApi =
        retrofit.create(CurrentWeatherApi::class.java)

    @Provides
    @AppScope
    fun provideCurrentWeatherRemoteSource(weatherApi: CurrentWeatherApi): CurrentWeatherRemoteSource =
        CurrentWeatherRemoteSource(weatherApi)

    @Provides
    @AppScope
    fun provideRemoteCurrentWeatherRepo(currentWeatherRemoteSource: CurrentWeatherRemoteSource): RemoteCurrentWeatherRepo =
        RemoteCurrentWeatherRepoImpl(currentWeatherRemoteSource)

    @Provides
    @AppScope
    fun provideSearchHintsApi(retrofit: Retrofit): SearchHintsApi =
        retrofit.create(SearchHintsApi::class.java)

    @Provides
    @AppScope
    fun provideSearchHintsRemoteSource(searchHintsApi: SearchHintsApi): SearchHintsRemoteSource =
        SearchHintsRemoteSource(searchHintsApi)

    @Provides
    @AppScope
    fun provideRemoteSearchHintsRepo(searchHintsRemoteSource: SearchHintsRemoteSource): RemoteSearchHintsRepo =
        RemoteSearchHintsRepoImpl(searchHintsRemoteSource)

    @Provides
    @AppScope
    fun provideWeekWeatherApi(retrofit: Retrofit): WeekWeatherApi =
        retrofit.create(WeekWeatherApi::class.java)

    @Provides
    @AppScope
    fun provideWeekWeatherRemoteSource(weatherApi: WeekWeatherApi): WeekWeatherRemoteSource =
        WeekWeatherRemoteSource(weatherApi)

    @Provides
    @AppScope
    fun provideRemoteWeekWeatherRepo(weekWeatherRemoteSource: WeekWeatherRemoteSource): RemoteWeekWeatherRepo =
        RemoteWeekWeatherRepoImpl(weekWeatherRemoteSource)
}