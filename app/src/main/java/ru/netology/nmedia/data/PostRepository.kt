package ru.netology.nmedia.data

import androidx.lifecycle.LiveData

interface PostRepository {

    val data: LiveData<List<Post>>

    fun liked(postId: Int)

    fun shareClicked(postId: Int)
}