package com.example.arcanittest.data.network.model

import com.google.gson.annotations.SerializedName

data class RepoSearchResponse(
    @SerializedName("items") val items: List<RepoDto> = emptyList(),
)
