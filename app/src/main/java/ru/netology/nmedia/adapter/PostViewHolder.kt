package ru.netology.nmedia.adapter

import android.opengl.Visibility
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.countersCorrector
import java.math.RoundingMode
import java.text.DecimalFormat

class PostViewHolder(
    private val binding: CardPostBinding,
    private val actionListener: ActionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            postText.text = post.content
            published.text = post.published
//            likeCounter.text = countersCorrector(post.likesCounter)
//            shareCounter.text = countersCorrector(post.sharesCounter)
            like.isChecked = post.likedByMe
            like.text = countersCorrector(post.likesCounter)
            shares.text = countersCorrector(post.sharesCounter)
            video.visibility = if (post.video.isNotEmpty()) View.VISIBLE else View.GONE

            playButton.setOnClickListener {
                actionListener.onVideoClick(post)
            }

            postText.setOnClickListener{
                actionListener.onPostClick(post)
            }

            picture.setOnClickListener {
                actionListener.onVideoClick(post)
            }

            like.setOnClickListener {
                actionListener.onLikeClick(post)
            }

            shares.setOnClickListener {
                actionListener.onShareClick(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                actionListener.onRemoveClick(post)
                                true
                            }
                            R.id.edit -> {
                                actionListener.onEditClick(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}

