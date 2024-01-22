package com.example.ngebacot.core.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthModel(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String
){
    companion object{
        fun fromJson(json: String): AuthModel{
            return kotlinx.serialization.json.Json.decodeFromString(serializer(), json)
        }
    }
    fun toJson(): String {
        return kotlinx.serialization.json.Json.encodeToString(serializer(), this)
    }
}