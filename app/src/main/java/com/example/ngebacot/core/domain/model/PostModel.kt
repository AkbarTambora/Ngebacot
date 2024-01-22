package com.example.ngebacot.core.domain.model

data class PostModel (
    val id: Int,
    val caption: String,
    val img: String,
    val userId: Int,
    val created_at: Int,
    val name: String,
    val ProfilePic: String
)