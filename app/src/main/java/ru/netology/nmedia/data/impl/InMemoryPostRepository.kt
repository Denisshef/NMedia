package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    private val posts
        get() = checkNotNull(data.value) {
            "Not null value"
        }

    override val data = MutableLiveData(
        List(10) { index ->
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
}