package com.example.testappv.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.text.Html
import com.example.testappv.data.db.News

class Util {
    companion object {

        private const val ITEM_WORD_LENGTH = 6

        fun isConnectedInternet(application: Application): Boolean {
            val connectivityManager =
                application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo = connectivityManager.activeNetworkInfo
            return (networkInfo != null) && networkInfo.isConnectedOrConnecting
        }

        fun xmlToListOfNews(xml: String): List<News> {
            val result = mutableListOf<News>()

            val firstItem = xml.findAnyOf(listOf("<item>"))
            val lastItem = xml.findLastAnyOf(listOf("</item>"))

            if (firstItem != null && lastItem != null) {
                val substring = xml.substring(firstItem.first + ITEM_WORD_LENGTH, lastItem.first)
                val newsListString = substring.split("</item>")

                newsListString.forEach {
                    val news = News()
                    news.title = it.substringAfter("<title>").substringBefore("</title>")
                    news.link = it.substringAfter("<link>").substringBefore("</link>")
                    news.description =
                        it.substringAfter("<description>").substringBefore("</description>")
                    news.pubDate = it.substringAfter("<pubDate>").substringBefore("</pubDate>")
                    news.category = it.substringAfter("<category>").substringBefore("</category>")
                    news.imageUrl = it.substringAfter("<enclosure url=\"").substringBefore("\" ")
                    news.fullText = it.substringAfter("<yandex:full-text>")
                        .substringBefore("</yandex:full-text>")
                    result.add(news)
                }
            }
            return result
        }

        fun htmlTextFrom(text: String) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        else
            Html.fromHtml(text)


    }
}