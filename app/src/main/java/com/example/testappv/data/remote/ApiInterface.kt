package com.example.testappv.data.remote

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiInterface {

    @GET("vesti.rss/")
    fun fetchNewsAsync(): Deferred<String>

}