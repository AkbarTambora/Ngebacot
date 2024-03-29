package com.example.ngebacot.core.domain.model

import com.example.ngebacot.core.utils.DateUtils
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class UserModel (
    val id: Int,
    val email: String,
    val username: String,
    val name: String?,
    val coverpic: String?,
    val profilepic: String?,
    val city: String?,
    val website: String?,
    val created_at: String,
){
    // Mengonversi string tanggal dan waktu ke LocalDateTime menggunakan DateUtil
    val createdAts: LocalDateTime
        get() = DateUtils().localDateTime(created_at)
}