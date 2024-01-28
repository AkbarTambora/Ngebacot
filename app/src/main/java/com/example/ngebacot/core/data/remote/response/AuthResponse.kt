    package com.example.ngebacot.core.data.remote.response

    import com.example.ngebacot.core.domain.model.UserModel
    import kotlinx.serialization.SerialName
    import kotlinx.serialization.Serializable

    @Serializable
    data class AuthResponse (
        @SerialName("jwt-token")
        val jwtToken: String,
        val user: UserModel
    )