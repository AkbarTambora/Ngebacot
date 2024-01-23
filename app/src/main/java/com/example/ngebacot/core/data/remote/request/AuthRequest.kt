package com.example.ngebacot.core.data.remote.request

import kotlinx.serialization.SerialName

data class AuthRequest (
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String
)