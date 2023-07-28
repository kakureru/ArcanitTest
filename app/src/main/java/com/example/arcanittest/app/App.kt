package com.example.arcanittest.app

import android.app.Application
import com.example.arcanittest.app.di.appModule
import com.example.arcanittest.app.di.dataModule
import com.example.arcanittest.app.di.domainModule
import com.example.arcanittest.app.di.navigationModule
import com.example.arcanittest.app.di.networkModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            modules(listOf(appModule, dataModule, domainModule, navigationModule, networkModule))
        }
    }
}