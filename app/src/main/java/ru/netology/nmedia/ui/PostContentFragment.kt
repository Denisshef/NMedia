package ru.netology.nmedia.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import ru.netology.nmedia.databinding.ActivityPostContentBinding

class PostContentFragment : Fragment() {
    private val initialContent
    get() = requireArguments().getString(INITIAL_CONTENT_ARGUMENTS_KEY)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ActivityPostContentBinding.inflate(layoutInflater, container, false).also { binding ->
        binding.edit.setText(initialContent)
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            val text = binding.edit.text
            if (!text.isNullOrBlank()) {
                val resultBundle = Bundle(1)
                resultBundle.putString(REQUEST_KEY, text.toString())
                setFragmentResult(REQUEST_KEY, resultBundle)
            }
            parentFragmentManager.popBackStack()
        }
    }.root


    companion object {
        private const val INITIAL_CONTENT_ARGUMENTS_KEY = "initialContent"

        const val RESULT_NEW_KEY = "postNewContent"
        const val RESULT_EDIT_KEY = "postEditContent"
        const val REQUEST_KEY = "requestKey"

        fun postContentByFragment(initialContent: String?) = PostContentFragment().apply {
            arguments = Bundle(1).also {
                it.putString(INITIAL_CONTENT_ARGUMENTS_KEY, initialContent)
            }
        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}