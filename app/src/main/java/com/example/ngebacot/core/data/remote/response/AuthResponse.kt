package com.example.ngebacot.core.data.remote.response

import kotlinx.serialization.SerialName

data class AuthResponse (
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String
)