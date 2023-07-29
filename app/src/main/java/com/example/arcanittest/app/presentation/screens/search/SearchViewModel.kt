package com.example.arcanittest.app.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcanittest.app.presentation.screens.search.model.DelegateItem
import com.example.arcanittest.app.presentation.screens.search.model.RepoDelegateItem
import com.example.arcanittest.app.presentation.screens.search.model.UserDelegateItem
import com.example.arcanittest.app.presentation.screens.search.model.toUI
import com.example.arcanittest.domain.model.Repo
import com.example.arcanittest.domain.model.Searchable
import com.example.arcanittest.domain.model.User
import com.example.arcanittest.domain.usecase.GetUserAndRepoSearchResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getUserAndRepoSearchResultUseCase: GetUserAndRepoSearchResultUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun onSearchTextChanged(text: String) {
        _uiState.update { it.copy(query = text, isSearchButtonEnabled = text.isInputCorrect()) }
    }

    fun onSearchClick() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, isSearchButtonEnabled = false, isSearchFieldEnabled = false) }
        val result = getUserAndRepoSearchResultUseCase(uiState.value.query).mapNotNull { it.toDelegateItemOrNull() }
        _uiState.update { it.copy(searchResult = result, isLoading = false, isSearchButtonEnabled = true, isSearchFieldEnabled = true) }
    }

    private fun String.isInputCorrect() = isNotBlank() && trim().length >= 3
    private fun Searchable.toDelegateItemOrNull(): DelegateItem? =
        when (this) {
            is User -> this.toDelegateItem()
            is Repo -> this.toDelegateItem()
            else -> null
        }

    private fun User.toDelegateItem() = UserDelegateItem(id = id, value = this.toUI())
    private fun Repo.toDelegateItem() = RepoDelegateItem(id = id, value = this.toUI())
}