package com.example.arcanittest.app.presentation.screens.content.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.arcanittest.R
import com.example.arcanittest.app.presentation.screens.content.ContentItem
import com.example.arcanittest.databinding.ItemContentBinding
import com.example.arcanittest.domain.model.ContentType

class ContentAdapter(
    private val contentCallback: ContentCallback,
) : ListAdapter<ContentItem, ContentAdapter.ContentViewHolder>(DiffCallback) {

    inner class ContentViewHolder(private val binding: ItemContentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentItem) {
            with(binding) {
                name.text = item.name
                image.setContentTypeImage(item.type)
            }
        }

        private fun ImageView.setContentTypeImage(contentType: ContentType) {
            val imageResource = when (contentType) {
                ContentType.FILE -> R.drawable.ic_file
                ContentType.DIR -> R.drawable.ic_folder
            }
            this.load(imageResource)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder =
        ContentViewHolder(ItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION)
                contentCallback.onClick(item)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ContentItem>() {
            override fun areItemsTheSame(oldItem: ContentItem, newItem: ContentItem) = oldItem.path == newItem.path
            override fun areContentsTheSame(oldItem: ContentItem, newItem: ContentItem) = oldItem == newItem
        }
    }
}