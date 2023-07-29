package com.example.arcanittest.app.di

import com.example.arcanittest.domain.usecase.GetUserAndRepoSearchResultUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        GetUserAndRepoSearchResultUseCase(
            reposRepository = get(),
            usersRepository = get(),
        )
    }
}