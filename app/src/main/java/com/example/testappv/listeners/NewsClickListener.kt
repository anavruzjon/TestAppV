package com.example.testappv.listeners

import com.example.testappv.data.db.News

class NewsClickListener(val listener: (newsId: Long, newsTitle: String?) -> Unit) {
    fun onClick(item: News) = listener(item.id, item.title)
}