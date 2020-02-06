package com.example.testappv.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var title: String? = "",
    var link: String? = "",
    var description: String? = "",
    var pubDate: String? = "",
    var category: String? = "",
    var imageUrl: String? = "",
    var fullText: String? = ""
)