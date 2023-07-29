package com.example.arcanittest.app.presentation.screens.file

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcanittest.domain.repository.ReposRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FileViewModel(
    private val repoId: Long,
    private val path: String,
    private val reposRepository: ReposRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<FileUiState> = MutableStateFlow(FileUiState())
    val uiState: StateFlow<FileUiState> = _uiState.asStateFlow()

    init {
        loadFile()
    }

    private fun loadFile() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        val file = reposRepository.getFile(repoId, path)
        _uiState.update { it.copy(url = file.url, isLoading = false ) }
    }
}