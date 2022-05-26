package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)

        binding.postsContainer.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.saveButton.setOnClickListener {
            viewModel.onSaveButtonClicked(binding.contentEditText.text.toString())

            binding.contentEditText.clearFocus()
            binding.contentEditText.hideKeyboard()
            binding.groupCancelEdit.visibility = View.GONE
        }

        binding.cancelEdit.setOnClickListener {
            viewModel.onCancelEdit()
            binding.groupCancelEdit.visibility = View.GONE
            binding.contentEditText.text?.clear()
        }

        viewModel.currentPost.observe(this) { currentPost ->
            binding.contentEditText.setText(currentPost?.content)
            if (currentPost != null) {
                binding.groupCancelEdit.visibility = View.VISIBLE
                binding.editMessage.text = currentPost.author
            }
        }
    }
}