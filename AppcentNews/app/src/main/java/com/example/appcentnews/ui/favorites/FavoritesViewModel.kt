package com.example.appcentnews.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appcentnews.core.data.model.ArticleItem
import com.example.appcentnews.core.repository.FavoriteNewsRepository

class FavoritesViewModel : ViewModel() {
    private val repository = FavoriteNewsRepository()
    private val _newsArticles = MutableLiveData<List<ArticleItem>?>()
    val likedNewsList: LiveData<List<ArticleItem>?> = _newsArticles

    fun getLocalNews() {
        repository.getLocalNews(this)
    }

    fun onNewsFetched(newsArticles: List<ArticleItem?>?) {
        if (newsArticles != null) {
            _newsArticles.value = newsArticles.filterNotNull()
        }
    }
}
