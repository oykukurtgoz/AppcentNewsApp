package com.example.appcentnews.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appcentnews.core.data.model.ArticleItem
import com.example.appcentnews.core.repository.NewsRepository
import com.example.appcentnews.core.network.RetrofitManager
import com.example.appcentnews.utils.Constants.DEFAULT_NEWS

class HomeViewModel : ViewModel() {

    private val newsRepository = NewsRepository(RetrofitManager.newsService)
    private val _newsArticles = MutableLiveData<List<ArticleItem>?>()
    val newsArticles: LiveData<List<ArticleItem>?> = _newsArticles
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        callSearchQuery(DEFAULT_NEWS)
    }

    fun callSearchQuery(query: String) {
        newsRepository.searchNews(query, ::onNewsFetched, ::onNewsFetchError)
    }

    private fun onNewsFetched(newsArticles: List<ArticleItem>?) {
        _newsArticles.value = newsArticles
        if (newsArticles.isNullOrEmpty()) {
            _errorMessage.value = "No news found"
        } else {
            _errorMessage.value = null
        }
    }

    private fun onNewsFetchError(errorMessage: String?) {
        _errorMessage.value = errorMessage
    }
}
