package com.example.testappv.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface NewsDao {

    @Query("select * from news where category like :category")
    fun newsList(category: String): LiveData<List<News>>

    @Query("delete from news")
    fun clear()

    @Insert(onConflict = REPLACE)
    fun insert(news: List<News>)

    @Query("select count(id) from news")
    fun newsCount(): Long

    @Query("select * from news where id = :id")
    fun newsById(id: Long): LiveData<News>

}