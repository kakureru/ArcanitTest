package com.example.arcanittest.app.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcanittest.R
import com.example.arcanittest.app.presentation.adapter.DelegateItem
import com.example.arcanittest.app.presentation.navigation.Screens
import com.example.arcanittest.app.presentation.runCatchingNonCancellation
import com.example.arcanittest.app.presentation.screens.search.adapter.RepoDelegateItem
import com.example.arcanittest.app.presentation.screens.search.adapter.UserDelegateItem
import com.example.arcanittest.app.presentation.screens.search.model.toUI
import com.example.arcanittest.domain.model.Repo
import com.example.arcanittest.domain.model.Searchable
import com.example.arcanittest.domain.model.User
import com.example.arcanittest.domain.repository.UsersRepository
import com.example.arcanittest.domain.usecase.GetSearchResultUseCase
import com.example.arcanittest.domain.usecase.RequestMoreUseCase
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val router: Router,
    private val usersRepository: UsersRepository,
    private val getSearchResultUseCase: GetSearchResultUseCase,
    private val requestMoreUseCase: RequestMoreUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _effect: MutableSharedFlow<SearchEffect> = MutableSharedFlow()
    val effect: SharedFlow<SearchEffect> = _effect.asSharedFlow()

    init {
        subscribeToSearchResultChanges()
    }

    private fun subscribeToSearchResultChanges() {
        getSearchResultUseCase()
            .onEach { result -> updateUi(result) }
            .launchIn(viewModelScope)
    }

    private fun updateUi(result: List<Searchable>) {
        val data = result.mapNotNull { it.toDelegateItemOrNull() }
        _uiState.update {
            it.copy(
                data = data,
                isLoading = false,
                isSearchButtonEnabled = true,
                isSearchFieldEnabled = true
            )
        }
    }

    fun onSearchTextChanged(text: String) {
        _uiState.update { it.copy(query = text, isSearchButtonEnabled = text.isInputCorrect()) }
    }

    fun onSearchClick() = viewModelScope.launch {
        runCatchingNonCancellation {
            _uiState.update {
                it.copy(isLoading = true, isSearchButtonEnabled = false, isSearchFieldEnabled = false)
            }
            requestMoreUseCase(uiState.value.query, PAGE_SIZE)
        }.onFailure {
            _uiState.update {
                it.copy(isLoading = false, error = SearchError.Search(msg = R.string.error_searching))
            }
        }
    }

    fun onRepoClick(repoId: Long) {
        router.navigateTo(Screens.Content(repoId))
    }

    fun onTryAgainClick() {
        val error = uiState.value.error
        _uiState.update { it.copy(error = null) }
        when(error) { // в зависимости от ошибки, повторяем нужное действие
            is SearchError.Search -> onSearchClick()
            is SearchError.UserLoading -> onUserClick(error.userId)
            null -> Unit
        }
    }

    fun onUserClick(userId: Long) {
        viewModelScope.launch {
            runCatchingNonCancellation {
                _uiState.update { it.copy(isLoading = true) }
                val user = usersRepository.getUser(userId) // загружаем юзера, чтобы узнать его url
                _effect.emit(SearchEffect.OpenUserPage(user.url)) // посылаем ивент, что нужно открыть страницу
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = SearchError.UserLoading(msg = R.string.error_user_loading, userId = userId)
                    )
                }
            }
        }
    }

    fun onScroll(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) // проверяем нужно ли загружать еще
            viewModelScope.launch {
                requestMoreUseCase(uiState.value.query, PAGE_SIZE)
            }
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

    companion object {
        private const val VISIBLE_THRESHOLD = 5
        private const val PAGE_SIZE = 20
    }
}