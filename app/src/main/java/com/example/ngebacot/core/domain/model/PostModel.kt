package com.example.ngebacot.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PostModel (
    val caption: String,
    val img: String,
)