package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = PostAdapter(
            {viewModel.likeById(it.id)},
            {viewModel.shareById(it.id)}
        )
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }


//            binding.apply{
//                like.setOnClickListener {
//                    viewModel.like()
//                post.likedByMe = !post.likedByMe
//                like.setImageResource(
//                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
//                )
//                if (post.likedByMe) post.likesCounter++ else post.likesCounter--
//                likeCounter.text = countersCorrector(post.likesCounter)
//                }
//
//                shares.setOnClickListener {
//                    viewModel.shareById()
//                post.sharesCounter++
//                shareCounter.text = countersCorrector(post.sharesCounter)
//                }
            }
//        }





