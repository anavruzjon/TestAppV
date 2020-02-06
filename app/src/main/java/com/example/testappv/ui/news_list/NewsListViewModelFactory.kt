package com.example.testappv.ui.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testappv.data.repositories.NewsRepository

class NewsListViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsListViewModel::class.java))
            return NewsListViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}