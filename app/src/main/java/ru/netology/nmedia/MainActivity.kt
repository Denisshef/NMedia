package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import ru.netology.nmedia.databinding.PostViewBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PostViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            binding.render(post)
        }

        binding.like.setOnClickListener {
            viewModel.clickLikedPost()
        }

        binding.share.setOnClickListener {
            viewModel.clickSharePost()
        }
    }

    private fun PostViewBinding.render(post: Post) {
        author.text = post.author
        date.text = post.date
        content.text = post.content
        like.setImageResource(setImageResource(post.clickLike))
        amountLike.text = displayAmountIntToString(post.amountLike)
        amountShare.text = displayAmountIntToString(post.amountShare)
        amountVisibility.text = displayAmountIntToString(post.amountVisibility)
    }

    @DrawableRes
    private fun setImageResource(clickLike: Boolean) =
        if (clickLike) R.drawable.ic_baseline_liked_24 else R.drawable.ic_baseline_like_24


    private fun displayAmountIntToString(i: Int): String {
        val str = i.toString()
        return when (i) {
            in 1000..1099 -> "1${getString(R.string.kilo)}"
            in 1100..9999 -> "${str[0]}.${str[1]}${getString(R.string.kilo)}"
            in 10000..99999 -> "${str[0]}${str[1]}${getString(R.string.kilo)}"
            in 100000..999999 -> "${str[0]}${str[1]}${str[2]}${getString(R.string.kilo)}"
            in 1000000..1099999 -> "${str[0]}${getString(R.string.million)}"
            in 1100000..999999999 -> "${str[0]}.${str[1]}${getString(R.string.million)}"
            else -> "$i"
        }
    }
}