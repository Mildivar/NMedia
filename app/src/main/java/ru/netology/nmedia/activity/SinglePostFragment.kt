package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.FragmentSinglePostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.countersCorrector
import ru.netology.nmedia.util.LongArg
import ru.netology.nmedia.util.viewBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class SinglePostFragment : Fragment(R.layout.fragment_single_post) {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )


//    private var _binding: FragmentFeedBinding? = null
//    val binding: FragmentFeedBinding
//        get() = _binding!!

//    private var param1: Long? = null

    private val binding by viewBinding(FragmentSinglePostBinding::bind)

    //    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//         _binding = FragmentFeedBinding.inflate(inflater)
//        return binding.root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.data.value?.find {
             it.id == arguments?.idArg
         }!!


        viewModel.data.observe(viewLifecycleOwner) {
            val singlePost = it.find { post ->
                post.id == arguments?.idArg!!
            }
            if (singlePost != null) {
                fillPost(singlePost)
            }
        }
    }

    private fun fillPost(post: Post) {
        post.apply {
            binding.postContent.let { cardPost ->

                cardPost.author.text = this.author
                cardPost.postText.text = this.content
                cardPost.published.text = this.published
                cardPost.like.isChecked = this.likedByMe == true
                cardPost.like.text = countersCorrector(this.likesCounter)
                cardPost.shares.text = countersCorrector(this.sharesCounter)
                cardPost.video.visibility =
                    if (this.video.isNotEmpty()) View.VISIBLE else View.GONE

                cardPost.like.setOnClickListener {
                    viewModel.likeById(post.id)
                }

                cardPost.menu.setOnClickListener {
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.options_post)
                        setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.remove -> {
                                    viewModel.removeById(post.id)

                                    findNavController().navigateUp()
                                }
                                R.id.edit -> {
                                    viewModel.edit(post)
                                    val text = post.content
                                    findNavController().navigate(R.id.action_singlePostFragment_to_newPostFragment,
                                        Bundle().apply {
                                            textArg = text
                                        })

                                    true
                                }
                                else -> false
                            }
                        }
                    }.show()
                }

                cardPost.shares.setOnClickListener {
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
            }
        }
    }

    companion object {
        var Bundle.idArg: Long by LongArg
    }
}
