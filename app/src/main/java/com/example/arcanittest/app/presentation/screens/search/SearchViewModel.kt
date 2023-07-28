package com.example.arcanittest.app.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcanittest.app.presentation.adapter.DelegateItem
import com.example.arcanittest.app.presentation.screens.search.adapter.RepoDelegateItem
import com.example.arcanittest.app.presentation.screens.search.adapter.UserDelegateItem
import com.example.arcanittest.app.presentation.screens.search.model.toUI
import com.example.arcanittest.domain.model.Repo
import com.example.arcanittest.domain.model.User
import com.example.arcanittest.domain.usecase.GetReposSearchResultUseCase
import com.example.arcanittest.domain.usecase.GetUsersSearchResultUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

typealias SearchResult = List<DelegateItem>

class SearchViewModel(
    private val getUsersSearchResultUseCase: GetUsersSearchResultUseCase,
    private val getReposSearchResultUseCase: GetReposSearchResultUseCase,
) : ViewModel() {

    private val searchQueryState = MutableSharedFlow<String>()

    private val _searchResult = MutableStateFlow<SearchResult>(emptyList())
    val searchResult: StateFlow<SearchResult> = _searchResult.asStateFlow()

    init {
        subscribeToSearchQueryChanges()
    }

    fun onSearchTextChanged(text: String) {
        viewModelScope.launch { searchQueryState.emit(text) }
    }

    private fun subscribeToSearchQueryChanges() {
        searchQueryState
            .debounce(500L)
            .distinctUntilChanged()
            .filter { it.isEmpty() || it.isNotBlank() }
            .flatMapLatest { flowOf(search(it)) }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    private fun search(query: String) {
        _searchResult.value = getDelegatedList(getUsersSearchResultUseCase(query), getReposSearchResultUseCase(query))
    }

    private fun getDelegatedList(users: List<User>, repos: List<Repo>): List<DelegateItem> = buildList {
        addAll(users.map { it.toDelegateItem() })
        addAll(repos.map { it.toDelegateItem() })
    }

    private fun User.toDelegateItem() = UserDelegateItem(id = id, value = this.toUI())
    private fun Repo.toDelegateItem() = RepoDelegateItem(id = id, value = this.toUI())
}