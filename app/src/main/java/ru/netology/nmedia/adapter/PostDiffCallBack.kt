package ru.netology.nmedia.adapter

import ru.netology.nmedia.dto.Post
import androidx.recyclerview.widget.DiffUtil as DiffUtil

class PostDiffCallBack : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}