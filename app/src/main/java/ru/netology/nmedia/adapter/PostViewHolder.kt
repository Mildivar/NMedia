package ru.netology.nmedia.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import java.math.RoundingMode
import java.text.DecimalFormat

class PostViewHolder(
    private val binding: CardPostBinding,
    private val likeListener: OnLikeListener,
    private val shareListener: OnShareListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            likeCounter.text = countersCorrector(post.likesCounter)
            shareCounter.text = countersCorrector(post.sharesCounter)
            like.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24)

            like.setOnClickListener {
                likeListener(post)
            }

            shares.setOnClickListener {
                shareListener(post)
            }
        }
    }
}

fun countersCorrector(item: Int): String {
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