package com.example.arcanittest.app.presentation.screens.search

import com.example.arcanittest.app.presentation.adapter.DelegateItem

data class SearchUiState(
    val isLoading: Boolean = false,
    val error: SearchError? = null,
    val query: String = "",
    val isSearchButtonEnabled: Boolean = false,
    val isSearchFieldEnabled: Boolean = true,
    val data: List<DelegateItem> = emptyList(),
)

sealed class SearchError(open val msg: Int) {
    class Search(override val msg: Int) : SearchError(msg)
    class UserLoading(override val msg: Int, val userId: Long) : SearchError(msg)
}

sealed class SearchEffect {
    class OpenUserPage(val url: String) : SearchEffect()
}