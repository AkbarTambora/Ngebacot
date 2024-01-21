package com.example.ngebacot.core.data.remote.client

import com.example.ngebacot.core.data.remote.response.AuthResponse
import com.example.ngebacot.core.data.remote.response.LoginResponse
import com.example.ngebacot.core.data.remote.response.RegisterResponse
import com.example.ngebacot.core.domain.model.PostModel
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    //End Point Register
    @POST("api/auth/register")
    suspend fun register(
        @Body RequestBody: String
    ): Response<AuthResponse>

    //Endpoint Login
    @POST("api/auth/login")
    suspend fun login(
        @Body RequestBody: LoginResponse
    ): Response<AuthResponse>

    @POST("api/auth/logout")
    suspend fun logout(): ResponseBody

    @POST("/api/posts")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Body postRequest: PostModel
    ): Response<PostModel>

    companion object
}