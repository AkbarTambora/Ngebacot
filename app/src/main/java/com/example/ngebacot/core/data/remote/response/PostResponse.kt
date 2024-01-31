package com.example.ngebacot.core.data.remote.response

import PostModel
import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    val data: List<PostModel>
)
