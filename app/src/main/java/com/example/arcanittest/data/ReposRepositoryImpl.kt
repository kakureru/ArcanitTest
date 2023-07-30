package com.example.arcanittest.data

import com.example.arcanittest.data.network.service.ReposService
import com.example.arcanittest.domain.model.Content
import com.example.arcanittest.domain.model.GitFile
import com.example.arcanittest.domain.model.Repo
import com.example.arcanittest.domain.model.toDomain
import com.example.arcanittest.domain.repository.ReposRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class ReposRepositoryImpl(
    private val reposService: ReposService,
) : ReposRepository {

    private val inMemoryCache = mutableListOf<Repo>()
    private val searchResults = MutableSharedFlow<List<Repo>>(replay = 1)
    private var lastRequestedPage = GITHUB_STARTING_PAGE_INDEX
    private var requestInProgress = false

    override fun getSearchResultStream(): Flow<List<Repo>> {
        lastRequestedPage = GITHUB_STARTING_PAGE_INDEX
        inMemoryCache.clear()
        return searchResults
    }

    override suspend fun requestMore(query: String, pageSize: Int) {
        if (requestInProgress) return
        requestInProgress = true
        val apiQuery = "$query $IN_QUALIFIER"
        val repos = reposService.searchRepos(apiQuery, lastRequestedPage, pageSize).items.map { it.toDomain() }
        inMemoryCache.addAll(repos)
        searchResults.emit(inMemoryCache)
        lastRequestedPage++
        requestInProgress = false
    }

    override suspend fun getContent(repoId: Long, path: String?): List<Content> {
        val repoDto = reposService.getRepo(repoId)
        return reposService.getContent(repoDto.owner.login, repoDto.name, path ?: "").map { it.toDomain() }
    }

    override suspend fun getFile(repoId: Long, path: String): GitFile {
        val repoDto = reposService.getRepo(repoId)
        return reposService.getFile(repoDto.owner.login, repoDto.name, path).toDomain()
    }

    companion object {
        private const val IN_QUALIFIER = "in:name"
        private const val GITHUB_STARTING_PAGE_INDEX = 1
    }
}