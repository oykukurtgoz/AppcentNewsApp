package com.example.appcentnews.core.repository

import com.example.appcentnews.ui.favorites.FavoritesViewModel
import com.example.appcentnews.utils.SharedPreferencesManager

class FavoriteNewsRepository() {

    fun getLocalNews(viewModel: FavoritesViewModel) {
        val newsList =  SharedPreferencesManager.getSavedArticles()
        viewModel.onNewsFetched(newsList)
    }
}