package ru.netology.nmedia.ui

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.databinding.PostContentFragmentBinding

class PostContentFragment : Fragment() {

    private val args by navArgs<PostContentFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostContentFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        binding.edit.setText(args.initialContent)
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            val text = binding.edit.text
            if (!text.isNullOrBlank()) {
                val resultBundle = Bundle(1)
                resultBundle.putString(REQUEST_KEY, text.toString())
                setFragmentResult(REQUEST_KEY, resultBundle)
            }
            findNavController().popBackStack()
        }
    }.root

    companion object {
        const val REQUEST_KEY = "requestKey"
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}