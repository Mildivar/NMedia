package ru.netology.nmedia.dto

data class Post(
    val id:Int,
    val author:String,
    val content:String,
    val published:String,
    val likedByMe:Boolean,
    var likesCounter:Int,
    var sharesCounter:Int
)
