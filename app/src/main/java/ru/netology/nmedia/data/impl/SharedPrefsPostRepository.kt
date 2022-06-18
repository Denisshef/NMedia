package ru.netology.nmedia.data.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlin.properties.Delegates

class SharedPrefsPostRepository(
    application: Application
) : PostRepository {

    private val pref = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE
    )
    private var nextId: Int by Delegates.observable(
        pref.getInt(NEXT_ID_PREFS_KEY, 0)
    ) { _, _, newValue ->
        pref.edit { putInt(NEXT_ID_PREFS_KEY, newValue) }
    }

    private var posts
        get() = checkNotNull(data.value) {
            "Not null value"
        }
        set(value) {
            pref.edit {
                val serializedPosts = Json.encodeToString(value)
                putString(POSTS_PREFS_KEY, serializedPosts)
            }
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val serializedPosts = pref.getString(POSTS_PREFS_KEY, null)
        val posts: List<Post> = if (serializedPosts != null) {
            Json.decodeFromString(serializedPosts)
        } else emptyList()
        data = MutableLiveData(posts)

    }

    override fun like(postId: Int) {
        posts = posts.map {
            if (it.id == postId) {
                if (it.clickLike)
                    it.copy(clickLike = !it.clickLike, amountLike = it.amountLike - 1)
                else
                    it.copy(clickLike = !it.clickLike, amountLike = it.amountLike + 1)
            } else it
        }
    }

    override fun shareClicked(postId: Int) {
        posts = posts.map {
            if (it.id == postId) it.copy(amountShare = it.amountShare + 1)
            else it
        }
    }

    override fun delete(postId: Int) {
        posts = posts.filterNot { it.id == postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {
        posts = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }

    private fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it

        }
    }

    companion object {
        const val POSTS_PREFS_KEY = "posts"
        const val NEXT_ID_PREFS_KEY = "idPost"
    }
}