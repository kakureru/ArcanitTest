package com.example.arcanittest.domain.usecase

import com.example.arcanittest.domain.model.User
import com.example.arcanittest.domain.repository.UsersRepository

class GetUsersSearchResultUseCase(private val usersRepository: UsersRepository) {
    operator fun invoke(query: String): List<User> = usersRepository.getUsers()
}