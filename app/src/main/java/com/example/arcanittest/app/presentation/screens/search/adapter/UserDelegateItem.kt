package com.example.arcanittest.app.presentation.screens.search.adapter

import com.example.arcanittest.app.presentation.adapter.DelegateItem
import com.example.arcanittest.app.presentation.screens.search.model.UserItem

class UserDelegateItem(val id: Long, private val value: UserItem) : DelegateItem {
    override fun content(): Any = value
    override fun id(): Long = id
    override fun compareToOther(other: DelegateItem): Boolean =
        (other as UserDelegateItem).content() == value
}