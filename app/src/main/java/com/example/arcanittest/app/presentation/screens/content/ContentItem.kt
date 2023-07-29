package com.example.arcanittest.app.presentation.screens.content

import com.example.arcanittest.R
import com.example.arcanittest.domain.model.Content
import com.example.arcanittest.domain.model.ContentType

data class ContentItem(
    val path: String,
    val name: String,
    val typeImage: Int,
)

fun Content.toUI() = ContentItem(
    path = path,
    name = name,
    typeImage = when (type) {
        ContentType.FILE -> R.drawable.ic_file
        ContentType.DIR -> R.drawable.ic_folder
    }
)