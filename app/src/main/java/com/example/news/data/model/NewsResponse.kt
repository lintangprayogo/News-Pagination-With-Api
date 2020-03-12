package com.example.news.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse (@SerializedName("articles") val news: List<News>)