package com.example.arcanittest.data

import com.example.arcanittest.data.network.service.UsersService
import com.example.arcanittest.domain.model.User
import com.example.arcanittest.domain.model.toDomain
import com.example.arcanittest.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class UsersRepositoryImpl(
    private val usersService: UsersService,
) : UsersRepository {

    private val inMemoryCache = mutableListOf<User>()
    private val searchResults = MutableSharedFlow<List<User>>(replay = 1)
    private var lastRequestedPage = GITHUB_STARTING_PAGE_INDEX
    private var requestInProgress = false

    override fun getSearchResultStream(): Flow<List<User>> {
        lastRequestedPage = GITHUB_STARTING_PAGE_INDEX
        inMemoryCache.clear()
        return searchResults
    }

    override suspend fun requestMore(query: String, pageSize: Int) {
        if (requestInProgress) return
        requestInProgress = true
        val apiQuery = "$query $IN_QUALIFIER"
        val repos = usersService.searchUsers(apiQuery, lastRequestedPage, pageSize).items.map { it.toDomain() }
        inMemoryCache.addAll(repos)
        searchResults.emit(inMemoryCache)
        lastRequestedPage++
        requestInProgress = false
    }

    override suspend fun getUser(userId: Long): User = usersService.getUser(userId).toDomain()

    companion object {
        private const val IN_QUALIFIER = "in:login"
        private const val GITHUB_STARTING_PAGE_INDEX = 1
    }
}