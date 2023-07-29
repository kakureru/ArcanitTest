package com.example.arcanittest.app.presentation.screens.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.arcanittest.app.presentation.screens.search.model.DelegateItem
import com.example.arcanittest.app.presentation.screens.search.model.UserDelegateItem
import com.example.arcanittest.app.presentation.screens.search.model.UserItem
import com.example.arcanittest.databinding.CardUserBinding

class UserDelegate : AdapterDelegate {

    inner class UserViewHolder(private val binding: CardUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserItem) {
            with(binding) {
                avatar.load(item.avatarUrl)
                login.text = item.login
                score.text = item.score
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        UserViewHolder(CardUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem, position: Int) {
        (holder as UserViewHolder).bind(item.content() as UserItem)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is UserDelegateItem
}