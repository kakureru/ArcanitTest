package com.example.arcanittest.domain.usecase

import com.example.arcanittest.domain.model.Searchable
import com.example.arcanittest.domain.repository.ReposRepository
import com.example.arcanittest.domain.repository.UsersRepository

class GetUserAndRepoSearchResultUseCase(
    private val usersRepository: UsersRepository,
    private val reposRepository: ReposRepository,
) {
    suspend operator fun invoke(query: String): List<Searchable> = buildList {
        addAll(usersRepository.searchUsers(query))
        addAll(reposRepository.searchRepos(query))
    }.sortedBy { it.searchableField().lowercase() }
}