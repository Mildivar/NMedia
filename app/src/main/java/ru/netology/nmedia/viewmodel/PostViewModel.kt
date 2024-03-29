package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepositoryImpl

val empty = Post(
    id = 0L,
    author = "Me",
    content = "",
    published = "",
    likedByMe = false,
    video = "",
    likesCounter = 0,
    sharesCounter = 0
)

class PostViewModel (application: Application):AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryImpl(
        AppDb.getInstance(application).postDao()
    )
    val data = repository.getAll()
    fun likeById(id:Long) = repository.likeById(id)
    fun shareById(id:Long) = repository.shareById(id)
    fun removeById(id:Long) = repository.removeById(id)

    private val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let{
            repository.save(it)
            edited.value = empty
        }
    }

    fun edit(post:Post){
        edited.value = post
    }

    fun changeContent(content:String){
        edited.value?.let{
            if (it.content == content) {
                return
            } else {
                edited.value = it.copy(content = content)
            }
        }
    }
}