package com.example.ngebacot.core.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse (
    val email: String,
    val username: String,
    val password: String
)