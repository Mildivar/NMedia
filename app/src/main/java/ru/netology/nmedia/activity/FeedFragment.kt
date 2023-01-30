package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.activity.SinglePostFragment.Companion.idArg
import ru.netology.nmedia.adapter.ActionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    private var _binding:FragmentFeedBinding? = null
    private val binding:FragmentFeedBinding
    get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(
            layoutInflater,
            container,
            false
        )

        val adapter = PostAdapter(
            object : ActionListener {

                override fun onLikeClick(post: Post) {
                    if (!post.likedByMe) {
                        viewModel.likeById(post.id)
                    } else viewModel.unlikeById(post.id)
                }

                override fun onShareClick(post: Post) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }
                    viewModel.shareById(post.id)

                    val shareIntent =
                        Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    startActivity(shareIntent)
                }

                override fun onRemoveClick(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEditClick(post: Post) {
                    viewModel.edit(post)
                    val text = post.content
                    findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply {
                       textArg = text
                    })
        }

                override fun onVideoClick(post: Post) {
                    val webpage: Uri = Uri.parse(post.video)
                    val intent = Intent(Intent.ACTION_VIEW, webpage)
                    startActivity(intent)
                }

                override fun onPostClick(post: Post) {
                    findNavController().navigate(R.id.action_feedFragment_to_singlePostFragment,
                    Bundle().apply
                     { idArg = post.id })
                }
            }
        )
        binding.list.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.posts)
            binding.allStates.progress.isVisible = state.loading
            binding.allStates.errorGroup.isVisible = state.error
            binding.allStates.emptyText.isVisible = state.empty
        }

        binding.allStates.retryButton.setOnClickListener{
            viewModel.loadPosts()
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //объект для передачи текста
//    companion object {
//        private const val TEXT_KEY = "TEXT_KEY"
//        var Bundle.textArg:String?
//            set(value) = putString(TEXT_KEY,value)
//            get() = getString(TEXT_KEY)
//    }
}



//убрать клавиатуру
object AndroidUtils {
    fun hideKeyBoard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}





