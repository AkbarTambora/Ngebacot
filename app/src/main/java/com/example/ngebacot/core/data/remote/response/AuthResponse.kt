package com.example.ngebacot.core.data.remote.response

import com.example.ngebacot.core.domain.model.UserModel

data class AuthResponse (
    val jwtToken: String,
    val user:UserModel
)