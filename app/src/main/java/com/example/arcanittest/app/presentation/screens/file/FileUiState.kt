package com.example.arcanittest.app.presentation.screens.file

data class FileUiState(
    val isLoading: Boolean = false,
    val error: Int? = null,
    val url: String? = null,
)