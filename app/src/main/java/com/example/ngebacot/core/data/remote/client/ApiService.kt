package com.example.ngebacot.core.data.remote.client

import com.example.ngebacot.core.data.remote.request.LoginRequest
import com.example.ngebacot.core.data.remote.request.RegisterRequest
import com.example.ngebacot.core.data.remote.response.AuthResponse
import com.example.ngebacot.core.data.remote.response.PostResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    //End Point Register
    @POST("api/auth/register")
    suspend fun register(
        @Body requestBody: RegisterRequest
    ): Response<AuthResponse>

    //Endpoint Login
    @POST("api/auth/login")
    suspend fun login(
        @Body requestBody: LoginRequest
    ): Response<AuthResponse>

    @POST("api/auth/logout")
    suspend fun logout(): ResponseBody

    @POST("/api/posts")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Body postRequest: PostResponse
    ): Response<PostResponse>
}