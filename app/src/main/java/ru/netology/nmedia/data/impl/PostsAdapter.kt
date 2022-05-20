package ru.netology.nmedia.data.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.App
import ru.netology.nmedia.databinding.PostViewBinding

internal class PostsAdapter(
    private val onLikeClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffPosts) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = PostViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: PostViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) = with(binding) {
            author.text = post.author
            date.text = post.date
            content.text = post.content
            like.setImageResource(setImageResource(post.clickLike))
            amountLike.text = displayAmountIntToString(post.amountLike)
            amountShare.text = displayAmountIntToString(post.amountShare)
            amountVisibility.text = displayAmountIntToString(post.amountVisibility)
            like.setOnClickListener { onLikeClicked(post) }
            share.setOnClickListener { onShareClicked(post) }
        }


        @DrawableRes
        private fun setImageResource(clickLike: Boolean) =
            if (clickLike) R.drawable.ic_baseline_liked_24 else R.drawable.ic_baseline_like_24

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