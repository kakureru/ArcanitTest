package com.example.arcanittest.domain.model

import com.example.arcanittest.data.network.model.ContentTypeData

enum class ContentType {
    FILE,
    DIR,
}

fun ContentTypeData.toDomain() = when(this) {
    ContentTypeData.DIR -> ContentType.DIR
    ContentTypeData.FILE -> ContentType.FILE
}