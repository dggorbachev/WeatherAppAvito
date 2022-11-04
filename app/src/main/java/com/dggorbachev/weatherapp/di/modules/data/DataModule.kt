package com.dggorbachev.weatherapp.di.modules.data

import android.app.Application
import com.dggorbachev.weatherapp.di.annotations.AppScope
import com.dggorbachev.weatherapp.features.preferences_manager.PreferencesManager
import dagger.Module
import dagger.Provides

@Module(includes = [LocalDataModule::class, RemoteDataModule::class])
object DataModule {

    @Provides
    @AppScope
    fun providePreferencesManager(
        application: Application,
    ): PreferencesManager =
        PreferencesManager(application)
}