package com.example.arcanittest.data.network.model

import com.google.gson.annotations.SerializedName

data class RepoDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("forks_count") val forksCount: Int,
    @SerializedName("owner") val owner: UserDto,
)