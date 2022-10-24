package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        subscribe()
        setupListeners()
    }

private fun subscribe() {
    viewModel.data.observe(this) { post ->
        binding.apply {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            like.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24)

            likeCounter.text = countersCorrector(post.likesCounter)
            shareCounter.text = countersCorrector(post.sharesCounter)
        }
    }
}
    private fun setupListeners(){
            binding.apply{
                like.setOnClickListener {
                    viewModel.like()
//                post.likedByMe = !post.likedByMe
//                like.setImageResource(
//                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
//                )
//                if (post.likedByMe) post.likesCounter++ else post.likesCounter--
//                likeCounter.text = countersCorrector(post.likesCounter)
                }

                shares.setOnClickListener {
                    viewModel.share()
//                post.sharesCounter++
//                shareCounter.text = countersCorrector(post.sharesCounter)
                }
            }
        }
        }


private fun countersCorrector(item: Int): String {
    return when (item) {
        in 1000..9999 -> "${roundOffDecimal(item / 1000.0)}K"
        in 10_000..999_999 -> "${(item / 1000)}K"
        in 1_000_000..1_000_000_000 -> "${roundOffDecimal(item / 1_000_000.0)}M"
        else -> "$item"
    }
}

private fun roundOffDecimal(number: Double): String {
    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(number).toString()
}


