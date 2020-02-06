package com.example.testappv.ui.news_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testappv.data.repositories.NewsRepository

class NewsDetailViewModelFactory(
    private val repository: NewsRepository,
    private val newsId: Long,
    private val newsTitle: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java))
            return NewsDetailViewModel(repository, newsId, newsTitle) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}