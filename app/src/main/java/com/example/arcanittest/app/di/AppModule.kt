package com.example.arcanittest.app.di

import com.example.arcanittest.app.presentation.screens.content.ContentViewModel
import com.example.arcanittest.app.presentation.screens.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        SearchViewModel(
            router = get(),
            getUserAndRepoSearchResultUseCase = get(),
        )
    }

    viewModel { params ->
        ContentViewModel(
            repoId = params[0],
            path = params[1],
            router = get(),
            reposRepository = get(),
        )
    }
}