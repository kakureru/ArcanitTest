package com.example.arcanittest.app

import android.app.Application
import com.example.arcanittest.app.di.navigationModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            modules(listOf(navigationModule))
        }
    }
}