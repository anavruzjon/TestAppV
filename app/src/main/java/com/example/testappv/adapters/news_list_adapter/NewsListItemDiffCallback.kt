package com.example.testappv.adapters.news_list_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.testappv.data.db.News

class NewsListItemDiffCallback : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }

}