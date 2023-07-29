package com.example.arcanittest.data

import com.example.arcanittest.data.network.service.ReposService
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

    companion object {
        const val IN_QUALIFIER = "in:name"
    }
}