package com.example.arcanittest.data

import com.example.arcanittest.domain.model.Repo
import com.example.arcanittest.domain.repository.ReposRepository

class ReposRepositoryImpl : ReposRepository {
    override fun getRepos(): List<Repo> {
        return listOf(
            Repo(1, "first", 4, "bla bla"),
            Repo(2, "second", 6, "bla bla"),
            Repo(3, "third", 9, "bla bla"),
        )
    }
}