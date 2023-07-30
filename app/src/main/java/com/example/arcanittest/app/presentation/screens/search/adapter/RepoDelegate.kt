package com.example.arcanittest.app.presentation.screens.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.arcanittest.R
import com.example.arcanittest.app.presentation.adapter.AdapterDelegate
import com.example.arcanittest.app.presentation.adapter.DelegateItem
import com.example.arcanittest.app.presentation.screens.search.model.RepoItem
import com.example.arcanittest.databinding.CardRepoBinding

class RepoDelegate(
    private val repoCallback: RepoCallback,
) : AdapterDelegate {

    inner class RepoViewHolder(
        private val context: Context,
        private val binding: CardRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RepoItem) {
            with(binding) {
                name.text = item.name
                forksCount.text = context.resources.getString(R.string.forks, item.forksCount)
                description.text = item.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        RepoViewHolder(parent.context, CardRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem, position: Int) {
        val repoItem = item.content() as RepoItem
        (holder as RepoViewHolder).bind(repoItem)
        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION)
                repoCallback.onClick(repoItem.id)
        }
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is RepoDelegateItem
}