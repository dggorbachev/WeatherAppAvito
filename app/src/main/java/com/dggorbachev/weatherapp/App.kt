package com.dggorbachev.weatherapp

import android.app.Application
import com.dggorbachev.weatherapp.di.AppComponent
import com.dggorbachev.weatherapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    private val component by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return initializeDaggerComponent()
    }

    private fun initializeDaggerComponent(): AppComponent {
        component.inject(this)

        return component
    }
}