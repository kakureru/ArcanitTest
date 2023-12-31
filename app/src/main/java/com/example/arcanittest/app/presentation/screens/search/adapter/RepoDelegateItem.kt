package com.example.arcanittest.app.presentation.screens.search.adapter

import com.example.arcanittest.app.presentation.adapter.DelegateItem
import com.example.arcanittest.app.presentation.screens.search.model.RepoItem

class RepoDelegateItem(val id: Long, private val value: RepoItem) : DelegateItem {
    override fun content(): Any = value
    override fun id(): Long = id
    override fun compareToOther(other: DelegateItem): Boolean =
        (other as RepoDelegateItem).content() == value
}