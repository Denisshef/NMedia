package ru.netology.nmedia

class Post(
    val author: String,
    val date: String,
    val content: String,
    var amountLike: Int,
    var amountShare: Int,
    var amountVisibility: Int,
    var clickLike: Boolean = false
)