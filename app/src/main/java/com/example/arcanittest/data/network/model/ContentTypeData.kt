package com.example.arcanittest.data.network.model

import com.google.gson.annotations.SerializedName

enum class ContentTypeData {
    @SerializedName("file") FILE,
    @SerializedName("dir") DIR,
}