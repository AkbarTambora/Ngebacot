package com.example.ngebacot.core.data.remote.client

import com.example.ngebacot.core.data.remote.response.AuthResponse
import com.example.ngebacot.core.data.remote.response.LoginResponse
import com.example.ngebacot.core.data.remote.response.RegisterResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    //End Point Register
    @POST("api/auth/register")
    suspend fun register(
        @Body RequestBody: RegisterResponse
    ): Response<AuthResponse>

    //Endpoint Login
    @POST("api/auth/login")
    suspend fun login(
        @Body RequestBody: LoginResponse
    ): Response<AuthResponse>

    @POST("api/auth/logout")
    suspend fun logout(): ResponseBody

    companion object
}