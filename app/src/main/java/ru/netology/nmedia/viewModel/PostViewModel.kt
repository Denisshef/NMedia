package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel: ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    fun clickLikedPost(post: Post) = repository.like(post.id)
    fun clickSharePost(post: Post) = repository.shareClicked(post.id)
    fun onDeleteClicked(post: Post) = repository.delete(post.id)
}