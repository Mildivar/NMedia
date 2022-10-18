package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       val viewModel:PostViewModel by viewModels()
        binding.apply {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            likeCounter.text = countersCorrector(post.likesCounter)
            shareCounter.text = countersCorrector(post.sharesCounter)

            like.setOnClickListener {
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
                if (post.likedByMe) post.likesCounter++ else post.likesCounter--
                likeCounter.text = countersCorrector(post.likesCounter)
            }

            shares.setOnClickListener {
                post.sharesCounter++
                shareCounter.text = countersCorrector(post.sharesCounter)
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
