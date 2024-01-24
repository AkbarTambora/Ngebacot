package com.example.ngebacot.core.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest (
    val username: String,
    val password: String
)