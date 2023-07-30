package com.example.arcanittest.app.presentation.screens.content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcanittest.R
import com.example.arcanittest.app.presentation.navigation.Screens
import com.example.arcanittest.app.presentation.runCatchingNonCancellation
import com.example.arcanittest.domain.model.ContentType
import com.example.arcanittest.domain.repository.ReposRepository
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContentViewModel(
    private val repoId: Long,
    private val path: String?,
    private val router: Router,
    private val reposRepository: ReposRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ContentUiState> =
        MutableStateFlow(ContentUiState(path = "$DEFAULT_PATH${path ?: ""}"))
    val uiState: StateFlow<ContentUiState> = _uiState.asStateFlow()

    init {
        loadContent()
    }

    /**
     * Плохо, что берем type из ui модели, теряется единый источник правды,
     * но я не успел придумать как сделать лучше, надеюсь это не очень страшно...
      */
    fun onContentClick(item: ContentItem) {
        when(item.type) {
            ContentType.FILE -> router.navigateTo(Screens.File(repoId, item.path))
            ContentType.DIR -> router.navigateTo(Screens.Content(repoId, item.path))
        }
    }

    fun onTryAgainClick() {
        _uiState.update { it.copy(error = null) }
        loadContent()
    }

    private fun loadContent() = viewModelScope.launch {
        runCatchingNonCancellation {
            _uiState.update { it.copy(isLoading = true) }
            val content = reposRepository.getContent(repoId, path).map { it.toUI() }
            _uiState.update { it.copy(content = content, isLoading = false) }
        }.onFailure {
            _uiState.update { it.copy(isLoading = false, error = R.string.error_content_loading) }
        }
    }

    companion object {
        private const val DEFAULT_PATH = "/"
    }
}