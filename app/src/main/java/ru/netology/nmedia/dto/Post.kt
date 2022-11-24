package ru.netology.nmedia.dto

data class Post(
    val id:Long,
    val author:String,
    val content:String,
    val published:String,
    val likedByMe:Boolean,
    val video:String,
    var likesCounter:Int,
    var sharesCounter:Int
)
