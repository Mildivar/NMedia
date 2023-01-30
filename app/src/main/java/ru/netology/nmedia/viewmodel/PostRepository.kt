package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll():List<Post>
    fun likeById(id:Long)
    fun unlikeById(id:Long)
    fun shareById(id:Long)
    fun removeById(id:Long)
    fun save(post:Post)
    fun postById(id: Long)
}