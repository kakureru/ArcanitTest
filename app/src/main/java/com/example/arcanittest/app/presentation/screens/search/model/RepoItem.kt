package com.example.arcanittest.app.presentation.screens.search.model

import com.example.arcanittest.domain.model.Repo

data class RepoItem(
    val id: Long,
    val name: String,
    val forksCount: String,
    val description: String?,
)

fun Repo.toUI() = RepoItem(
    id = id,
    name = name,
    forksCount = forksCount.toString(),
    description = description,
)