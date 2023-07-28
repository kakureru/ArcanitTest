package com.example.arcanittest.domain.usecase

import com.example.arcanittest.domain.model.Repo
import com.example.arcanittest.domain.repository.ReposRepository

class GetReposSearchResultUseCase(private val reposRepository: ReposRepository) {
    operator fun invoke(query: String): List<Repo> = reposRepository.getRepos()
}