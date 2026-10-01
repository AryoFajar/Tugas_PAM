package com.example.tugaspraktikum2.newsfeed.model

enum class Category {
    Technology,
    Sports,
    Business,
    Entertainment,
    Policy
}

data class News(
    val id: Int,
    val title: String,
    val category: Category,
    val timestamp: Long
)


data class NewsDetail(
    val id: Int,
    val fullContent: String,
    val author: String,
    val readTimeMinutes: Int
)

class NetworkException(message: String) : Exception(message)
