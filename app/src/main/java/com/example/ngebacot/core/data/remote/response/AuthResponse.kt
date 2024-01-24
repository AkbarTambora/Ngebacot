package com.example.ngebacot.core.data.remote.response

import com.example.ngebacot.core.domain.model.UserModel
import kotlinx.serialization.SerialName

data class AuthResponse (
    @SerialName("jwtToken")
    val jwtToken: String,
    @SerialName("user")
    val user: UserModel
)