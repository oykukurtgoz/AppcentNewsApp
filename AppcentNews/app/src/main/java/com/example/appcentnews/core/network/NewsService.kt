package com.example.appcentnews.core.network

import com.example.appcentnews.core.data.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/everything")
    fun getNews(@Query("q") keyword: String): Call<News>?
}