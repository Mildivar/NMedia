package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

interface ActionListener{
    fun onLikeClick(post:Post)
    fun onShareClick(post:Post)
    fun onRemoveClick(post: Post)
    fun onEditClick(post:Post)
    fun onVideoClick(post: Post)
    fun onPostClick(post: Post)
}

class PostAdapter(
    private val actionListener: ActionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view,actionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
