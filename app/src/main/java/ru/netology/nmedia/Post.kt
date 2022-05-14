package ru.netology.nmedia

data class Post(
    val author: String,
    val date: String,
    val content: String,
    val amountLike: Int,
    val amountShare: Int,
    val amountVisibility: Int,
    val clickLike: Boolean = false
)