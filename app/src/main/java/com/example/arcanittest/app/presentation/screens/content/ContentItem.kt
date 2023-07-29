package com.example.arcanittest.app.presentation.screens.content

import com.example.arcanittest.domain.model.Content
import com.example.arcanittest.domain.model.ContentType

data class ContentItem(
    val path: String,
    val name: String,
    val type: ContentType,
)

fun Content.toUI() = ContentItem(
    path = path,
    name = name,
    type = type,
)