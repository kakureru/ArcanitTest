package com.example.arcanittest.app.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.dsl.module

val navigationModule = module {
    single<Cicerone<Router>> {
        Cicerone.create()
    }

    single<Router> {
        provideRouter(cicerone = get())
    }

    single<NavigatorHolder> {
        provideNavigatorHolder(cicerone = get())
    }
}

private fun provideRouter(cicerone: Cicerone<Router>) = cicerone.router
private fun provideNavigatorHolder(cicerone: Cicerone<Router>) = cicerone.getNavigatorHolder()