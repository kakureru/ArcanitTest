package com.example.arcanittest.domain.model

import com.example.arcanittest.data.network.model.RepoDto

data class Repo(
    val id: Long,
    val name: String,
    val forksCount: Int,
    val description: String?,
) : Searchable {
    override fun searchableField(): String = name
}

fun RepoDto.toDomain() = Repo(
    id = id,
    name = name,
    forksCount = forksCount,
    description = description,
)