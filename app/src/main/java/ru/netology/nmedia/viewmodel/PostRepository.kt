package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun get():LiveData<List<Post>>
    fun likeById(id:Long)
    fun shareById(id:Long)
}