package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    private var _binding:FragmentNewPostBinding? = null
    private val binding: FragmentNewPostBinding
    get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         _binding = FragmentNewPostBinding.inflate(
            layoutInflater,
            container,
            false
        )

        binding.edit.requestFocus()

        arguments?.textArg?.let {
            binding.edit.setText(it)
        }

        binding.ok.setOnClickListener {
            if (!binding.edit.text.isNullOrBlank()) {
                val content = binding.edit.text.toString()
                viewModel.changeContent(content)
                viewModel.save()
                AndroidUtils.hideKeyBoard(requireView())
            }
        }
        viewModel.postCreated.observe(viewLifecycleOwner){
            viewModel.loadPosts()
            findNavController().navigateUp()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    companion object {
        var Bundle.textArg: String? by StringArg
    }
}



