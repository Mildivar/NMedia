package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val video:String,
    val likesCounter: Int = 0,
    val sharesCounter: Int = 0
) {
    fun toDto() = Post(id, author, content, published, likedByMe, video,likesCounter,sharesCounter)

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(dto.id, dto.author, dto.content, dto.published, dto.likedByMe,dto.video, dto.likesCounter, dto.sharesCounter)

    }
}