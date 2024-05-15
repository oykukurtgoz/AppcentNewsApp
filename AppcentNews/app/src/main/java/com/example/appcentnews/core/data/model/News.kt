package com.example.appcentnews.core.data.model

import com.google.gson.annotations.SerializedName

data class News(

    @field:SerializedName("totalResults")
    val totalResults: Int? = null,

    @field:SerializedName("articles")
    val articles: List<ArticleItem?>? = null,

    @field:SerializedName("status")
    val status: String? = null
)