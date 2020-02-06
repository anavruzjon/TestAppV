package com.example.testappv.adapters.news_list_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testappv.data.db.News
import com.example.testappv.databinding.ItemNewsBinding
import com.example.testappv.listeners.NewsClickListener

class NewsListAdapter(private val listener: NewsClickListener) :
    ListAdapter<News, RecyclerView.ViewHolder>(NewsListItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NewsViewHolder).bind(getItem(position), listener)
    }

    class NewsViewHolder private constructor(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: News, listener: NewsClickListener) {
            binding.item = item
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NewsViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemNewsBinding.inflate(inflater, parent, false)
                return NewsViewHolder(binding)
            }
        }
    }

}