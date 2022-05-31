package ru.netology.nmedia.adapter

import ru.netology.nmedia.data.Post

interface PostInteractionListener {

    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onDeleteClicked(post: Post)
    fun onEditClicked(post: Post)
    fun onPlayVideo(post: Post)
}