package com.example.arcanittest.app.presentation.screens.search.model

import com.example.arcanittest.domain.model.User

data class UserItem(
    val id: Int,
    val avatarUrl: String,
    val login: String,
    val score: String,
)

fun User.toUI() = UserItem(
    id = id,
    avatarUrl = avatarUrl,
    login = login,
    score = score,
)