package com.example.arcanittest.app.presentation.screens.content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcanittest.app.presentation.navigation.Screens
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

    fun onContentClick(contentPath: String) {
        router.navigateTo(Screens.Content(repoId, contentPath))
    }

    private fun loadContent() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        val content = reposRepository.getContent(repoId, path).map { it.toUI() }
        _uiState.update { it.copy(content = content, isLoading = false) }
    }

    companion object {
        private const val DEFAULT_PATH = "/"
    }
}