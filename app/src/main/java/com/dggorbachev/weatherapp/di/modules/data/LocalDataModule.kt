package com.dggorbachev.weatherapp.di.modules.data

import com.dggorbachev.weatherapp.data.local_location.LocalLocationRepo
import com.dggorbachev.weatherapp.data.local_location.LocalLocationRepoImpl
import com.dggorbachev.weatherapp.di.annotations.AppScope
import dagger.Module
import dagger.Provides

@Module
object LocalDataModule {

    @Provides
    @AppScope
    fun provideLocalLocationRepo(): LocalLocationRepo =
        LocalLocationRepoImpl()
}