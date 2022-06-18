package ru.netology.nmedia.adapter

import ru.netology.nmedia.data.Post

interface PostInteractionListener {

    fun onLikeClicked(postId: Int)
    fun onShareClicked(postId: Int)
    fun onDeleteClicked(postId: Int)
    fun onEditClicked(post: Post)
    fun onPlayVideo(post: Post)
    fun onSinglePost(post: Post)
}