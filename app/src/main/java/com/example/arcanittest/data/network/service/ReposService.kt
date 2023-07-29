package com.example.arcanittest.data.network.service

import com.example.arcanittest.data.network.model.ContentDto
import com.example.arcanittest.data.network.model.FileDto
import com.example.arcanittest.data.network.model.RepoDto
import com.example.arcanittest.data.network.model.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReposService {
    @GET("search/repositories")
    suspend fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): RepoSearchResponse

    @GET("repositories/{repo_id}")
    suspend fun getRepo(
        @Path(value = "repo_id", encoded = true) repoId: Long,
    ): RepoDto

    @GET("/repos/{owner}/{repo}/contents/{path}")
    suspend fun getContent(
        @Path(value = "owner", encoded = true) owner: String,
        @Path(value = "repo", encoded = true) repoName: String,
        @Path(value = "path", encoded = true) path: String,
    ): List<ContentDto>

    @GET("/repos/{owner}/{repo}/contents/{path}")
    suspend fun getFile(
        @Path(value = "owner", encoded = true) owner: String,
        @Path(value = "repo", encoded = true) repoName: String,
        @Path(value = "path", encoded = true) path: String,
    ): FileDto
}