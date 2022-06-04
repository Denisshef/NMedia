package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.SinglePostFragmentBinding

class SinglePostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = SinglePostFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        //findNavController().popBackStack()
    }.root

}