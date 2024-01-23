package com.example.ngebacot.core.data.remote.response

import kotlinx.serialization.SerialName

data class AuthResponse (
    @SerialName("jwtToken")
    val jwtToken: String,
)