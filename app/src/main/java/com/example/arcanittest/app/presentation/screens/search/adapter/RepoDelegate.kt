package com.example.arcanittest.app.presentation.screens.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.arcanittest.app.presentation.adapter.AdapterDelegate
import com.example.arcanittest.app.presentation.adapter.DelegateItem
import com.example.arcanittest.app.presentation.screens.search.model.RepoItem
import com.example.arcanittest.databinding.CardRepoBinding

class RepoDelegate : AdapterDelegate {

    inner class RepoViewHolder(private val binding: CardRepoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RepoItem) {
            with(binding) {
                name.text = item.name
                forksCount.text = item.forksCount
                description.text = item.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        RepoViewHolder(CardRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem, position: Int) {
        (holder as RepoViewHolder).bind(item.content() as RepoItem)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is RepoDelegateItem
}