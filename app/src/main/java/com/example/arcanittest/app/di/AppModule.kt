package com.example.arcanittest.app.di

import com.example.arcanittest.app.presentation.screens.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        SearchViewModel(getUserAndRepoSearchResultUseCase = get())
    }
}