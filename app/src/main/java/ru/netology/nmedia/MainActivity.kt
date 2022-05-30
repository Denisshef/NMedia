package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.launch
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

        binding.fag.setOnClickListener {
            viewModel.onAddClicked()
        }

        val postContentActivityLauncher = registerForActivityResult(
            PostContentActivity.ResultContract
        ) { postContent ->
            postContent ?: return@registerForActivityResult
            viewModel.onSaveButtonClicked(postContent)
        }
        viewModel.navigateToPostContentScreenEvent.observe(this) {
            postContentActivityLauncher.launch()
        }

//        binding.cancelEdit.setOnClickListener {
//            viewModel.onCancelEdit()
//            binding.groupCancelEdit.visibility = View.GONE
//            binding.contentEditText.text?.clear()
//        }

//        viewModel.currentPost.observe(this) { currentPost ->
//            binding.contentEditText.setText(currentPost?.content)
//            if (currentPost != null) {
//                binding.groupCancelEdit.visibility = View.VISIBLE
//                binding.editMessage.text = currentPost.author
//            }
//        }
    }
}