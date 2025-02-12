package com.example.appcentnews.core.data.model

import com.google.gson.annotations.SerializedName

data class Source(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Any? = null
)