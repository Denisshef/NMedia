package ru.netology.nmedia.data

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int,
    val author: String,
    val date: String,
    val video: String? = null,
    val content: String,
    val amountLike: Int,
    val amountShare: Int,
    val amountVisibility: Int,
    val clickLike: Boolean = false
)