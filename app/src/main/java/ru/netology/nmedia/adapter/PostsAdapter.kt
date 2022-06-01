package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.App
import ru.netology.nmedia.databinding.PostViewBinding

internal class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffPosts) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = PostViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: PostViewBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post
        private val popupMenu =
            PopupMenu(itemView.context, binding.optionView).apply {
                inflate(R.menu.option_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onDeleteClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }

        init {
            binding.like.setOnClickListener { listener.onLikeClicked(post) }
            binding.share.setOnClickListener { listener.onShareClicked(post) }
            binding.videoContent.setOnClickListener { listener.onPlayVideo(post) }
            binding.buttonPlayVideo.setOnClickListener { listener.onPlayVideo(post) }
            binding.optionView.setOnClickListener { popupMenu.show() }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                author.text = post.author
                date.text = post.date
                video.visibility = if (post.video == null) View.GONE else View.VISIBLE
                content.text = post.content
                like.isChecked = post.clickLike
                like.text = displayAmountIntToString(post.amountLike)
                share.text = displayAmountIntToString(post.amountShare)
                visibility.text = displayAmountIntToString(post.amountVisibility)
            }
        }

        private fun displayAmountIntToString(i: Int): String {
            val str = i.toString()
            return when (i) {
                in 1000..1099 -> "1${App.instance.getString(R.string.kilo)}"
                in 1100..9999 -> "${str[0]}.${str[1]}${App.instance.getString(R.string.kilo)}"
                in 10000..99999 -> "${str[0]}${str[1]}${App.instance.getString(R.string.kilo)}"
                in 100000..999999 -> "${str[0]}${str[1]}${str[2]}${App.instance.getString(R.string.kilo)}"
                in 1000000..1099999 -> "${str[0]}${App.instance.getString(R.string.million)}"
                in 1100000..999999999 -> "${str[0]}.${str[1]}${App.instance.getString(R.string.million)}"
                else -> "$i"
            }
        }
    }

    private object DiffPosts : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}