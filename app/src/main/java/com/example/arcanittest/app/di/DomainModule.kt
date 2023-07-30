package com.example.arcanittest.app.di

import com.example.arcanittest.domain.usecase.GetSearchResultUseCase
import com.example.arcanittest.domain.usecase.RequestMoreUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        GetSearchResultUseCase(
            reposRepository = get(),
            usersRepository = get(),
        )
    }

    factory {
        RequestMoreUseCase(
            reposRepository = get(),
            usersRepository = get(),
        )
    }
}