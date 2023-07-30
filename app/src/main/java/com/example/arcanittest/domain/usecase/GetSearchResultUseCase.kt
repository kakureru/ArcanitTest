package com.example.arcanittest.domain.usecase

import com.example.arcanittest.domain.model.Searchable
import com.example.arcanittest.domain.repository.ReposRepository
import com.example.arcanittest.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetSearchResultUseCase(
    private val usersRepository: UsersRepository,
    private val reposRepository: ReposRepository,
) {
    operator fun invoke(): Flow<List<Searchable>> =
        combine(
            reposRepository.getSearchResultStream(),
            usersRepository.getSearchResultStream(),
        ) { repos, users ->
            (repos + users).sortedBy { it.searchableField().lowercase() }
        }
}