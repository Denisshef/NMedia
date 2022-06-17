package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Contacts.SettingsColumns.KEY
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ListAdapter
import kotlinx.serialization.descriptors.StructureKind
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FeedFragmentBinding
import ru.netology.nmedia.viewModel.PostViewModel

class FeedFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(
            requestKey = PostContentFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != PostContentFragment.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent = bundle.getString(PostContentFragment.REQUEST_KEY)
                ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(newPostContent)
        }

        setFragmentResultListener(
            requestKey = SinglePostFragment.KEY
        ){requestKey, bundle ->
            if(requestKey == SinglePostFragment.KEY){
               // adapter.submitList(viewModel.data.value)
            }
        }

        viewModel.navigateToPostContentScreenEvent.observe(this) { initialContent ->
            val directions = FeedFragmentDirections.toPostContentFragment(initialContent)
            findNavController().navigate(directions)
        }

        viewModel.navigateToSinglePostShow.observe(this) { singlePost ->
            findNavController().navigate(FeedFragmentDirections.toSinglePostFragment(singlePost.id, singlePost.content))
        }

        viewModel.playVideoPost.observe(this) {
            val playVideo = viewModel.playVideoPost.value?.video ?: return@observe
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(playVideo))
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FeedFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val adapter = PostsAdapter(viewModel)
        binding.postsContainer.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.fag.setOnClickListener {
            viewModel.onAddClicked()
        }
    }.root
}