package com.example.arcanittest.app.presentation.screens.content

data class ContentUiState(
    val isLoading: Boolean = false,
    val path: String = "",
    val content: List<ContentItem> = emptyList()
)