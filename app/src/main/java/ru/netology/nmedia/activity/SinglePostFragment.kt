package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.ActionListener
import ru.netology.nmedia.adapter.countersCorrector
import ru.netology.nmedia.databinding.FragmentSinglePostBinding
import ru.netology.nmedia.util.LongArg
import ru.netology.nmedia.util.viewBinding
import ru.netology.nmedia.viewmodel.PostViewModel

//private const val ARG_PARAM1 = "param1"

class SinglePostFragment : Fragment(R.layout.fragment_single_post) {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

//    private var _binding: FragmentFeedBinding? = null
//    val binding: FragmentFeedBinding
//        get() = _binding!!

//    private var param1: Long? = null

    val binding by viewBinding(FragmentSinglePostBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//         _binding = FragmentFeedBinding.inflate(inflater)
//        return binding.root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val post = viewModel.data.value?.find {
            it.id == arguments?.idArg
        }
        post.apply {
               binding.postContent.let { cardPost ->
                   lateinit var actionListener: ActionListener

                   cardPost.author.text = this?.author
                   cardPost.postText.text = this?.content
                   cardPost.published.text = this?.published
                   cardPost.like.isChecked = this?.likedByMe == true
                   cardPost.like.text =
                       this?.likesCounter?.let { counter -> countersCorrector(counter) }
                   cardPost.shares.text =
                       this?.sharesCounter?.let { counter -> countersCorrector(counter) }
                   cardPost.video.visibility =
                       if (this?.video?.isNotEmpty() == true) View.VISIBLE else View.GONE

                   cardPost.menu.setOnClickListener {
                       PopupMenu(it.context, it).apply {
                           inflate(R.menu.options_post)
                           setOnMenuItemClickListener { item ->
                               when (item.itemId) {
                                   R.id.remove -> {

                                       if (post != null) {
                                           actionListener.onRemoveClick(post)
                                       }

                                       findNavController().navigateUp()
                                   }
                                   R.id.edit -> {

                                       if (post != null) {
                                           actionListener.onEditClick(post)
                                       }

                                       true
                                   }
                                   else -> false
                               }
                           }
                       }.show()
                   }
               }

           }

        viewModel.data.observe(viewLifecycleOwner) {
            viewModel.data.value?.find { post?.id == arguments?.idArg }
        }
    }

    companion object {
        var Bundle.idArg: Long by LongArg


//        @JvmStatic
//        fun newInstance(param1: Long) =
//            SinglePostFragment().apply {
//                arguments = Bundle().apply {
//                    putLong(ARG_PARAM1, param1)
//                }
//
//            }
    }
}

//
//val adapter = PostAdapter(
//    object : ActionListener {
//
//        override fun onLikeClick(post: Post) {
//            viewModel.likeById(post.id)
//        }
//
//        override fun onShareClick(post: Post) {
//            val intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT, post.content)
//                type = "text/plain"
//            }
//            viewModel.shareById(post.id)
//
//            val shareIntent =
//                Intent.createChooser(intent, getString(R.string.chooser_share_post))
//            startActivity(shareIntent)
//        }
//
//        override fun onRemoveClick(post: Post) {
//            viewModel.removeById(post.id)
//        }
//
//        override fun onEditClick(post: Post) {
//            viewModel.edit(post)
//            val text = post.published
//            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
//                Bundle().apply {
//                    textArg = text
//                })
//        }
//
//        override fun onVideoClick(post: Post) {
//            val webpage: Uri = Uri.parse(post.video)
//            val intent = Intent(Intent.ACTION_VIEW, webpage)
//            startActivity(intent)
//        }
//
//        override fun onPostClick(post: Post) {
//            findNavController().navigate(R.id.action_feedFragment_to_singlePostFragment,
//                Bundle().apply
//                { idArg = post.id })
//        }
//    }
//)
//viewModel.data.observe(viewLifecycleOwner) { posts ->
//    adapter.submitList(posts)
//}