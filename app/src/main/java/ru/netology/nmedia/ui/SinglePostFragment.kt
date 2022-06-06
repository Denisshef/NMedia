package ru.netology.nmedia.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.SinglePostFragmentBinding
import ru.netology.nmedia.viewModel.PostViewModel

class SinglePostFragment : Fragment() {

    private val model by viewModels<PostViewModel>()
    private val args by navArgs<SinglePostFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = SinglePostFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val popupMenu = PopupMenu(context, binding.optionView).apply {
            inflate(R.menu.option_post)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.remove -> {
                        model.onDeleteClicked(args.singlePost)
                        val resultBundle = Bundle(1)
                        resultBundle.putBoolean(KEY, true)
                        setFragmentResult(KEY, resultBundle)
                        findNavController().popBackStack()
                        true
                    }
                    R.id.edit -> {
                        model.navigateToSinglePostShow.value?.let { model.onEditClicked(it) }
                        //findNavController().navigate(SinglePostFragment.toPostContentFragment())
                        true
                    }
                    else -> false
                }
            }
        }

        binding.render()

        binding.optionView.setOnClickListener{
            popupMenu.show()
        }

        binding.like.setOnClickListener {
            model.onLikeClicked(args.singlePost)
            binding.render()
        }

        binding.share.setOnClickListener {
            model.onShareClicked(args.singlePost)
            binding.render()
        }
    }.root

    private fun SinglePostFragmentBinding.render() {
        val post = model.getPost(args.singlePost)
        author.text = post?.author
        date.text = post?.date
        video.visibility = if (post?.video == null) View.GONE else View.VISIBLE
        content.text = post?.content
        share.text = post?.amountShare.toString()
        like.text = post?.amountLike.toString()
        like.isChecked = post?.clickLike ?: false
        visibility.text = post?.amountVisibility.toString()
    }

    companion object {
        const val KEY = "singlePost"
    }
}