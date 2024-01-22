package com.example.ngebacot.core.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthModel(
    @SerialName("jwtToken")
    val jwtToken : String,
    val user : UserModel
)