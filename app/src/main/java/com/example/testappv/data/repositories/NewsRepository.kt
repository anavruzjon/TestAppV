package com.example.testappv.data.repositories

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.testappv.data.db.NewsDatabase
import com.example.testappv.data.remote.ApiInterface
import com.example.testappv.ui.news_list.NewsListFragment
import com.example.testappv.ui.news_list.NewsListFragmentDirections
import com.example.testappv.utils.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository private constructor(
    private val database: NewsDatabase,
    private val network: ApiInterface,
    private val application: Application
) {

    companion object {

        const val POLITICS_NEWS_STRING = "Политика"
        const val SPORT_NEWS_STRING = "Спорт"
        const val ECONOMICS_NEWS_STRING = "Экономика"
        const val INCIDENTS_NEWS_STRING = "Проишествия"
        const val IN_THE_WORLD_NEWS_STRING = "В мире"
        const val SOCIETY_NEWS_STRING = "Общество"
        const val HI_TECH_NEWS_STRING = "Hi-Tech. Интернет"

        private var INSTANCE: NewsRepository? = null
        fun getRepository(
            database: NewsDatabase,
            network: ApiInterface,
            application: Application
        ): NewsRepository {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = NewsRepository(database, network, application)
                }
                return INSTANCE as NewsRepository
            }
        }
    }

    private val _isInternetConnected = MutableLiveData<Boolean>()
    val isInternetConnected: LiveData<Boolean> = _isInternetConnected

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _newsInCategory = MutableLiveData<Int>()
    val newsList = Transformations.switchMap(_newsInCategory) {
        val categoryString = when (it) {
            NewsListFragment.ALL_NEWS -> "%"
            NewsListFragment.POLITICS_NEWS -> POLITICS_NEWS_STRING
            NewsListFragment.SPORT_NEWS -> SPORT_NEWS_STRING
            NewsListFragment.ECONOMICS_NEWS -> ECONOMICS_NEWS_STRING
            NewsListFragment.INCIDENTS_NEWS -> INCIDENTS_NEWS_STRING
            NewsListFragment.IN_THE_WORLD_NEWS -> IN_THE_WORLD_NEWS_STRING
            NewsListFragment.SOCIETY_NEWS -> SOCIETY_NEWS_STRING
            NewsListFragment.HI_TECH_NEWS -> HI_TECH_NEWS_STRING
            else -> "%"
        }
        database.newsDao.newsList(categoryString)
    }

    private val _getNews = MutableLiveData<Long>()
    val news = Transformations.switchMap(_getNews) {
        it?.let {
            database.newsDao.newsById(it)
        }
    }

    fun notifyNewsCategoryChange(category: Int) {
        _newsInCategory.value = category
    }

    fun notifyGetNews(newsId: Long) {
        _getNews.value = newsId
    }

    suspend fun getNewsList() {
        _isRefreshing.postValue(true)
        if (Util.isConnectedInternet(application)) {
            _isInternetConnected.postValue(true)
            withContext(Dispatchers.IO) {
                try {

                    val networkResult = network.fetchNewsAsync().await()
                    val news = Util.xmlToListOfNews(networkResult)

                    database.newsDao.clear()
                    database.newsDao.insert(news)

                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    _isRefreshing.postValue(false)
                }
            }

        } else {
            _isInternetConnected.postValue(false)
            _isRefreshing.postValue(false)
        }

    }


}