package com.example.arcanittest.data.network.model

import com.google.gson.annotations.SerializedName

data class RepoSearchResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<RepoDto> = emptyList(),
    val nextPage: Int? = null // TODO зачем и нужно ли для юзеров?
)
