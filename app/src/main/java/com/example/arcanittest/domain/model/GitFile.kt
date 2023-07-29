package com.example.arcanittest.domain.model

import com.example.arcanittest.data.network.model.FileDto

data class GitFile(
    val url: String,
)

fun FileDto.toDomain() = GitFile(
    url = url
)