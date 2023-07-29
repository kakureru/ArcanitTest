package com.example.arcanittest.app.presentation.screens.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.arcanittest.app.presentation.screens.search.model.DelegateItem

interface AdapterDelegate {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem, position: Int)
    fun isOfViewType(item: DelegateItem): Boolean
}