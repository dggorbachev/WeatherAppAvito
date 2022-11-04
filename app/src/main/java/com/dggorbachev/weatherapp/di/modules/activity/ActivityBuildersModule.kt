package com.dggorbachev.weatherapp.di.modules.activity

import com.dggorbachev.weatherapp.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.dggorbachev.weatherapp.di.annotations.ActivityScope
import com.dggorbachev.weatherapp.di.modules.fragments.FragmentsBuilderModule

@Module
interface ActivityBuildersModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentsBuilderModule::class])
    fun contributeMainActivity(): MainActivity

}