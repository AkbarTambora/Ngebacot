package com.example.ngebacot.core.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Date {
    fun localDateTime(dateTimeString:String):
            LocalDateTime{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        return LocalDateTime.parse(dateTimeString,formatter)
    }
}