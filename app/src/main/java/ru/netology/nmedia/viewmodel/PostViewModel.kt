package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.PostRepositoryImpl
import ru.netology.nmedia.util.SingleLiveEvent
import java.io.IOException
import kotlin.concurrent.thread

val empty = Post(
    id = 0L,
    author = "Me",
    content = "",
    published = "",
    likedByMe = true,
    video = "",
    likes = 0,
    sharesCounter = 0
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
//    fun likeById(id: Long) = repository.likeById(id)
//    fun shareById(id: Long) = repository.shareById(id)
//    fun removeById(id: Long) = repository.removeById(id)

    private val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadPosts()
    }

    fun loadPosts() {
        thread {
            _data.postValue(FeedModel(loading = true))
            try {
                val posts = repository.getAll()
                FeedModel(posts = posts, empty = posts.isEmpty())
            } catch (e: IOException) {
                FeedModel(error = true)
            }.also(_data::postValue)
        }
    }

    fun save() {
        edited.value?.let {
            thread {
                repository.save(it)
                _postCreated.postValue(Unit) //возвращение на предыдущий экран
            }
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        edited.value?.let {
            if (it.content == content) {
                return
            } else {
                edited.value = it.copy(content = content)
            }
        }
    }

    fun likeById(id: Long) {
        thread { repository.likeById(id) }
    }

    fun unlikeById(id: Long) {
        thread { repository.unlikeById(id) }
    }

    fun shareById(id: Long) {
        thread { repository.shareById(id) }
    }

    fun removeById(id: Long) {
        thread {
            val old = _data.value?.posts.orEmpty()
            _data.postValue(
                data.value?.copy(posts = _data.value?.posts.orEmpty()
                    .filter { it.id != id }
                )
            )
            try {
                repository.removeById(id)
            } catch (e: IOException) {
                _data.postValue(_data.value?.copy(posts = old))
            }
        }
    }
}