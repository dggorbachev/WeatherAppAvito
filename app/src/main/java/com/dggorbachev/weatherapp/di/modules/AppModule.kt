package com.dggorbachev.weatherapp.di.modules

import com.dggorbachev.weatherapp.di.modules.activity.ActivityBuildersModule
import com.dggorbachev.weatherapp.di.modules.data.DataModule
import com.dggorbachev.weatherapp.di.network.NetworkModule
import dagger.Module

@Module(
    includes = [
        ActivityBuildersModule::class,
        NetworkModule::class,
        DataModule::class
    ]
)
class AppModule
