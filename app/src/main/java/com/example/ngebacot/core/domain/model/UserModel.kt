package com.example.ngebacot.core.domain.model

import java.time.LocalDateTime

data class UserModel (
    val id: Int,
    val email: String,
    val username: String,
    val name: String?,
    val coverpic: String?,
    val profilepic: String?,
    val city: String?,
    val website: String?,
    val created_at: LocalDateTime
)