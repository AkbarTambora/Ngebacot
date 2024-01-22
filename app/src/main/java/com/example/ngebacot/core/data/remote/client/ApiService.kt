package com.example.ngebacot.core.data.remote.client

import com.example.ngebacot.core.data.remote.response.AuthResponse
import com.example.ngebacot.core.data.remote.response.LoginResponse
import com.example.ngebacot.core.data.remote.response.PostResponse
import com.example.ngebacot.core.utils.AppConstants
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    companion object{
        /*
        * Add a function to create the ApiService instance
        */
        fun create(): ApiService{
            val retrofit = Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
    @POST("/api/posts")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Body postRequest: PostResponse
    ): Response<PostResponse>
}