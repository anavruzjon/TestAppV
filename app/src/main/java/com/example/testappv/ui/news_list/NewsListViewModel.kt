package com.example.testappv.ui.news_list

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.testappv.data.repositories.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsListViewModel(private val repository: NewsRepository) : ViewModel() {

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)
    val newsList = repository.newsList

    val isRefreshing = repository.isRefreshing
    val isInternetConnected = repository.isInternetConnected

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    init {
        updateNews()
    }

    fun updateNews() {
        viewModelScope.launch {
            repository.getNewsList()
            categoryMenuChange(NewsListFragment.ALL_NEWS)
        }
    }

    fun categoryMenuChange(categoryId: Int) {
        repository.notifyNewsCategoryChange(categoryId)
    }


}