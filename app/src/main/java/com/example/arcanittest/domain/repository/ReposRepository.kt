package com.example.arcanittest.domain.repository

import com.example.arcanittest.domain.model.Content
import com.example.arcanittest.domain.model.GitFile
import com.example.arcanittest.domain.model.Repo
import kotlinx.coroutines.flow.Flow

interface ReposRepository {
    fun getSearchResultStream(): Flow<List<Repo>>
    suspend fun requestMore(query: String, pageSize: Int)
    suspend fun getContent(repoId: Long, path: String?): List<Content>
    suspend fun getFile(repoId: Long, path: String): GitFile
}