package com.example.arcanittest.app.presentation.screens.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.arcanittest.app.presentation.adapter.AdapterDelegate
import com.example.arcanittest.app.presentation.adapter.DelegateItem
import com.example.arcanittest.app.presentation.screens.search.model.UserItem
import com.example.arcanittest.databinding.CardUserBinding

class UserDelegate(
    private val userCallback: UserCallback,
) : AdapterDelegate {

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
        val userItem = item.content() as UserItem
        (holder as UserDelegate.UserViewHolder).bind(userItem)
        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION)
                userCallback.onClick(userItem.id)
        }
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is UserDelegateItem
}