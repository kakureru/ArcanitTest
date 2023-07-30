package com.example.arcanittest.domain.usecase

import com.example.arcanittest.domain.repository.ReposRepository
import com.example.arcanittest.domain.repository.UsersRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class RequestMoreUseCase(
    private val usersRepository: UsersRepository,
    private val reposRepository: ReposRepository,
) {
    suspend operator fun invoke(query: String, pageSize: Int) {
        coroutineScope {
            launch { usersRepository.requestMore(query, pageSize) }
            launch { reposRepository.requestMore(query, pageSize) }
        }
    }
}