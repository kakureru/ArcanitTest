package com.example.arcanittest.domain.model

import com.example.arcanittest.data.network.model.ContentDto

data class Content(
    val path: String,
    val name: String,
    val type: ContentType,
)

fun ContentDto.toDomain() = Content(
    name = name,
    path = path,
    type = type.toDomain(),
)