package com.example.arcanittest.app.di

import com.example.arcanittest.domain.usecase.GetReposSearchResultUseCase
import com.example.arcanittest.domain.usecase.GetUsersSearchResultUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetUsersSearchResultUseCase(
            usersRepository = get(),
        )
    }

    factory {
        GetReposSearchResultUseCase(
            reposRepository = get(),
        )
    }
}