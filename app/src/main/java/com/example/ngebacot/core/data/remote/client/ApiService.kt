package com.example.ngebacot.core.data.remote.client

import PostModel
import com.example.ngebacot.core.data.remote.request.LoginRequest
import com.example.ngebacot.core.data.remote.request.RegisterRequest
import com.example.ngebacot.core.data.remote.response.AuthResponse
import com.example.ngebacot.core.data.remote.response.PostResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query

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

    // Endpoint POSTS

    //Created Post
    @POST("/api/posts")
    suspend fun postContent(
        @HeaderMap headers: Map<String, String>,
        @Query("userId") userId: Number,
        @Body content: PostModel
    ): Response<PostResponse>

    // Get All Post
    @GET("/api/posts")
    suspend fun getPosts(
        @HeaderMap headers: Map<String, String>
    ): Response<PostResponse>

}