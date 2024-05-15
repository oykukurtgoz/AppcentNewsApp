package com.example.appcentnews.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.appcentnews.core.data.model.ArticleItem
import com.example.appcentnews.utils.Constants.APP_NAME
import com.google.gson.Gson

object SharedPreferencesManager {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()
    }

    fun putArticle(key: String?, value: ArticleItem?) {
        val gson = Gson()
        val json = gson.toJson(value)
        sharedPreferencesEditor.putString(key, json).apply()
    }

    private fun getArticle(key: String?): ArticleItem? {
        val gson = Gson()
        val json = sharedPreferences.getString(key, null)
        return gson.fromJson(json, ArticleItem::class.java)
    }

    fun removeArticle(key: String?) {
        sharedPreferencesEditor.remove(key)
        sharedPreferencesEditor.apply()
    }

    fun isArticleSaved(key: String?): Boolean {
        return getArticle(key) != null
    }

    fun getSavedArticles(): List<ArticleItem> {
        val gson = Gson()
        val savedArticles = mutableListOf<ArticleItem>()
        for ((_, value) in sharedPreferences.all) {
            val articleJson = value as String
            val article = gson.fromJson(articleJson, ArticleItem::class.java)
            savedArticles.add(article)
        }
        return savedArticles
    }
}
