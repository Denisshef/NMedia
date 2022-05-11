package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    override val data = MutableLiveData(
        Post(
            author = "My article",
            date = "29.04.2022",
            content = "Very good article",
            clickLike = false,
            amountLike = 99999,
            amountShare = 999999,
            amountVisibility = 11563000
        )
    )

    override fun liked() {
        val currentPost = checkNotNull(data.value) {
            "Not null value"
        }

        data.value = currentPost.copy(
            clickLike = !currentPost.clickLike,
            amountLike = if (!currentPost.clickLike)
                ++currentPost.amountLike else --currentPost.amountLike
        )
    }

    override fun shareClicked() {
        val currentPost = checkNotNull(data.value) {
            "Not null value"
        }

        data.value = currentPost.copy(
            amountShare = ++currentPost.amountShare
        )
    }
}