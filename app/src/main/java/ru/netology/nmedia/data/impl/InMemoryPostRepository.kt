package ru.netology.nmedia.data.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    private var nextId = GENERATED_POSTS_AMOUNT

    private val posts
        get() = checkNotNull(data.value) {
            "Not null value"
        }

    override val data = MutableLiveData(
        List(GENERATED_POSTS_AMOUNT) { index ->
            Post(
                id = index + 1,
                author = "My article",
                date = "29.04.2022",
                content = "Content (post id = ${index + 1})",
                clickLike = false,
                amountLike = 99999,
                amountShare = 999999,
                amountVisibility = 11563000
            )
        }
    )

    override fun like(postId: Int) {
        data.value = posts.map {
            if (it.id == postId) {
                if (it.clickLike)
                    it.copy(clickLike = !it.clickLike, amountLike = it.amountLike - 1)
                else
                    it.copy(clickLike = !it.clickLike, amountLike = it.amountLike + 1)
            } else it
        }
    }

    override fun shareClicked(postId: Int) {
        data.value = posts.map {
            if (it.id == postId) it.copy(amountShare = it.amountShare + 1)
            else it
        }
    }

    override fun delete(postId: Int) {
        data.value = posts.filterNot { it.id == postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {
        data.value = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it

        }
    }

    companion object {
        const val GENERATED_POSTS_AMOUNT = 2
    }
}