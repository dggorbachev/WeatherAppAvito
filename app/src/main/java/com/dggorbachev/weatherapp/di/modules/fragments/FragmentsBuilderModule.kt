package com.dggorbachev.weatherapp.di.modules.fragments

import com.dggorbachev.weatherapp.features.current_weather.ui.view.CurrentWeatherFragment
import com.dggorbachev.weatherapp.features.search_screen.ui.view.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentsBuilderModule {

    @ContributesAndroidInjector
    fun contributeCurrentWeatherFragment(): CurrentWeatherFragment

    @ContributesAndroidInjector
    fun contributeSearchFragment(): SearchFragment
}