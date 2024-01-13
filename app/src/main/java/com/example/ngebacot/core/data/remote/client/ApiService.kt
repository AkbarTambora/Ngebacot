package com.example.ngebacot.core.data.remote.client

import com.example.ngebacot.core.data.remote.response.AuthResponse
import com.example.ngebacot.core.data.remote.response.LoginResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    //End Point Register
    @POST("register")
    suspend fun register(@Body request: RequestBody): ResponseBody

    //Endpoint Login
    @POST("login")
    suspend fun login(
        @Body RequestBody: LoginResponse
    ): Response<AuthResponse>

    @POST("logout")
    suspend fun logout(): ResponseBody

    companion object
}