package com.example.appcentnews.core.repository

import com.example.appcentnews.core.data.model.ArticleItem
import com.example.appcentnews.core.data.model.News
import com.example.appcentnews.core.network.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.KFunction1

class NewsRepository(private val newsApiService: NewsService) {

    fun searchNews(
        query: String,
        onSuccess: (List<ArticleItem>?) -> Unit,
        onError: (String?) -> Unit
    ) {
        val call = newsApiService.getNews(query)
        call?.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    onSuccess(newsResponse?.articles?.filterNotNull())
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = parseErrorMessage(errorBody)
                    onError(errorMessage)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                onError(t.message)
            }
        })
    }

    private fun parseErrorMessage(errorBody: String?): String {
        return try {
            val startIndex = errorBody?.indexOf("message") ?: -1
            val endIndex = errorBody?.indexOf("\"", startIndex + 10) ?: -1
            errorBody?.substring(startIndex + 10, endIndex) ?: "Failed to retrieve news. Please try again."
        } catch (e: Exception) {
            "Failed to retrieve news. Please try again."
        }
    }

}



