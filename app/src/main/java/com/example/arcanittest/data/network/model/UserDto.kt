package com.example.arcanittest.data.network.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: Long,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("login") val login: String,
    @SerializedName("score") val score: Float,
)