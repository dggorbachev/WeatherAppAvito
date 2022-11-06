package com.dggorbachev.weatherapp.di.modules.fragments

import com.dggorbachev.weatherapp.features.current_weather.ui.view.CurrentWeatherFragment
import com.dggorbachev.weatherapp.features.search_screen.ui.view.SearchFragment
import com.dggorbachev.weatherapp.features.week_weather.ui.view.WeekWeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentsBuilderModule {

    @ContributesAndroidInjector
    fun contributeCurrentWeatherFragment(): CurrentWeatherFragment

    @ContributesAndroidInjector
    fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    fun contributeWeekWeatherFragment(): WeekWeatherFragment
}