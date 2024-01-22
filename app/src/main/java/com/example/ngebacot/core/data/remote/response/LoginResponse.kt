package com.example.ngebacot.core.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String
)