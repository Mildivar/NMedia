package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

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

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun likeById(id:Long) = repository.likeById(id)
    fun shareById(id:Long) = repository.shareById(id)
    fun removeById(id:Long) = repository.removeById(id)

    val edited = MutableLiveData(empty)

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