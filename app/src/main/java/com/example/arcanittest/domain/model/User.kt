package com.example.arcanittest.domain.model

import com.example.arcanittest.data.network.model.UserDto

data class User (
    val id: Long,
    val avatarUrl: String?,
    val login: String,
    val score: Float,
) : Searchable {
    override fun searchableField(): String = login
}

fun UserDto.toDomain() = User(
    id = id,
    avatarUrl = avatarUrl,
    login = login,
    score = score,
)