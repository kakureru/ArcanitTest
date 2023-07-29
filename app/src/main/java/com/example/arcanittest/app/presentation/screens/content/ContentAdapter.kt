package com.example.arcanittest.app.presentation.screens.content

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.arcanittest.databinding.ItemContentBinding

class ContentAdapter(
    private val contentCallback: ContentCallback,
) : ListAdapter<ContentItem, ContentAdapter.ContentViewHolder>(DiffCallback) {

    inner class ContentViewHolder(private val binding: ItemContentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentItem) {
            with(binding) {
                name.text = item.name
                image.load(item.typeImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder =
        ContentViewHolder(ItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ContentAdapter.ContentViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION)
                contentCallback.onClick(item.path)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ContentItem>() {
            override fun areItemsTheSame(oldItem: ContentItem, newItem: ContentItem) = oldItem.path == newItem.path
            override fun areContentsTheSame(oldItem: ContentItem, newItem: ContentItem) = oldItem == newItem
        }
    }
}