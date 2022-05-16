package ru.netology.nmedia.data

data class Post(
    val id: Int,
    val author: String,
    val date: String,
    val content: String,
    val amountLike: Int,
    val amountShare: Int,
    val amountVisibility: Int,
    val clickLike: Boolean = false
)