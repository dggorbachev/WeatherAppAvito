package com.dggorbachev.weatherapp.di.modules.activity

import com.dggorbachev.weatherapp.MainActivity
import com.dggorbachev.weatherapp.di.annotations.ActivityScope
import com.dggorbachev.weatherapp.di.modules.fragments.FragmentsBuilderModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuildersModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentsBuilderModule::class])
    fun contributeMainActivity(): MainActivity

}