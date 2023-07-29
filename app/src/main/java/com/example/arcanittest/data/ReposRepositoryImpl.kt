package com.example.arcanittest.data

import com.example.arcanittest.data.network.model.ContentDto
import com.example.arcanittest.data.network.model.ContentTypeData
import com.example.arcanittest.data.network.service.ReposService
import com.example.arcanittest.domain.model.Content
import com.example.arcanittest.domain.model.ContentType
import com.example.arcanittest.domain.model.Repo
import com.example.arcanittest.domain.model.toDomain
import com.example.arcanittest.domain.repository.ReposRepository

class ReposRepositoryImpl(
    private val reposService: ReposService,
) : ReposRepository {
    override suspend fun searchRepos(query: String): List<Repo> {
        val apiQuery = "$query $IN_QUALIFIER"
        return reposService.searchRepos(apiQuery, 1, 30).items.map { it.toDomain() }
    }

    override suspend fun getContent(repoId: Long, path: String?): List<Content> {
        val repoDto = reposService.getRepo(repoId)
        return reposService.getContent(repoDto.owner.login, repoDto.name, path ?: "").map { it.toDomain() }
    }

    private fun ContentDto.toDomain() = Content(
        name = name,
        path = path,
        type = type.toDomain()
    )

    private fun ContentTypeData.toDomain() = when(this) {
        ContentTypeData.DIR -> ContentType.DIR
        ContentTypeData.FILE -> ContentType.FILE
    }

    companion object {
        const val IN_QUALIFIER = "in:name"
    }
}