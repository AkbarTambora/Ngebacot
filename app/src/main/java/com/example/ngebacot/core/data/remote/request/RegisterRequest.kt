package com.example.ngebacot.core.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest (
    val email : String,
    val username : String,
    val password : String
)