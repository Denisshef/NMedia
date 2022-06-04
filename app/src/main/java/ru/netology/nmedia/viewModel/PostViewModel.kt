package ru.netology.nmedia.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myapp.SingleLiveEvent
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.SharedPrefsPostRepository

class PostViewModel(
    application: Application
) : AndroidViewModel(application), PostInteractionListener {

    private val repository: PostRepository = SharedPrefsPostRepository(application)

    val data by repository::data

    val navigateToPostContentScreenEvent = SingleLiveEvent<String>()
    val navigateToSinglePostShow = MutableLiveData<Post>()
    val playVideoPost = MutableLiveData<Post?>(null)
    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return
        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Планета хищников (id=${currentPost.value?.id}",
            date = "31.05.2022",
            content = content,
            clickLike = false,
            amountLike = 0,
            amountShare = 0,
            amountVisibility = 0
        )
        repository.save(post)
        currentPost.value = null
    }

    fun onAddClicked() {
        navigateToPostContentScreenEvent.call()
    }

    // region PostInteractionListener

    override fun onLikeClicked(post: Post) {
        repository.like(post.id)
    }

    override fun onShareClicked(post: Post) = repository.shareClicked(post.id)
    override fun onDeleteClicked(post: Post) = repository.delete(post.id)
    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = post.content
    }

    override fun onPlayVideo(post: Post) {
        playVideoPost.value = post
    }

    override fun onSinglePost(post: Post) {
        navigateToSinglePostShow.value = post
    }

    // endregion PostInteractionListener
}