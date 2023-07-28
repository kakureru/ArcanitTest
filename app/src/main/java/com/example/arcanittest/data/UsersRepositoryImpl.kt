package com.example.arcanittest.data

import com.example.arcanittest.domain.model.User
import com.example.arcanittest.domain.repository.UsersRepository

class UsersRepositoryImpl : UsersRepository {
    override fun getUsers(): List<User> {
        return listOf(
            User(1, "", "Ivan", "123"),
            User(2, "", "Petr", "456"),
            User(3, "", "Sidr", "789"),
        )
    }
}