package com.example.testappv.ui.news_detail

import androidx.lifecycle.ViewModel
import com.example.testappv.data.repositories.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsDetailViewModel(
    private val repository: NewsRepository,
    private val newsId: Long,
    private val newsTitle: String
) : ViewModel() {

    private val job = Job()
    private val viewModelScope = CoroutineScope(job + Dispatchers.Main)
    val news = repository.news

    init {
        repository.notifyGetNews(newsId)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}

