package com.example.arcanittest.app.presentation.screens.content.adapter

import com.example.arcanittest.app.presentation.screens.content.ContentItem

interface ContentCallback {
    fun onClick(item: ContentItem)
}