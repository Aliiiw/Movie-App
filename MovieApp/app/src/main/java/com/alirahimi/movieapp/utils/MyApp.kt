package com.alirahimi.movieapp.utils

import android.app.Application
import com.alirahimi.movieapp.di.AppModule
import com.alirahimi.movieapp.di.AppModuleImplementation

class MyApp : Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImplementation(this)
    }
}