package com.example.ngebacot.core.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class UserModel (
    val id: Int,
    val email: String,
    val username: String,
    val name: String?,
    val coverpic: String?,
    val profilepic: String?,
    val city: String?,
    val website: String?,
    @SerialName("createdAt")
    @Contextual
    val created_at: LocalDateTime
)