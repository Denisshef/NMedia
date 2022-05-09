package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.BoolRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.netology.nmedia.databinding.PostViewBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PostViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            author = "My article",
            date = "29.04.2022",
            content = "Very good article",
            clickLike = false,
            amountLike = 99999,
            amountShare = 999999,
            amountVisibility = 11563000
        )

        binding.render(post)

        binding.like.setOnClickListener {
            binding.like.setImageResource(clickedLike(post))
            binding.render(post)
        }

        binding.share.setOnClickListener {
            post.amountShare++
            binding.render(post)
        }
    }

    private fun PostViewBinding.render(post: Post) {
        author.text = post.author
        date.text = post.date
        content.text = post.content
        amountLike.text = displayAmountIntToString(post.amountLike)
        amountShare.text = displayAmountIntToString(post.amountShare)
        amountVisibility.text = displayAmountIntToString(post.amountVisibility)
    }

    @DrawableRes
    private fun clickedLike(post: Post): Int {
        post.clickLike = !post.clickLike
        return if (post.clickLike) {
            post.amountLike++
            R.drawable.ic_baseline_liked_24
        } else {
            post.amountLike--
            R.drawable.ic_baseline_like_24
        }
    }

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