package com.example.arcanittest.app.presentation.screens.search

import com.example.arcanittest.app.presentation.adapter.DelegateItem

data class SearchUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val query: String = "",
    val isSearchButtonEnabled: Boolean = false,
    val isSearchFieldEnabled: Boolean = true,
    val searchResult: List<DelegateItem> = emptyList(),
)