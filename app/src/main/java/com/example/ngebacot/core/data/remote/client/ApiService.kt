package com.example.ngebacot.core.data.remote.client

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    //End Poiint Register
    @POST("register")
    suspend fun register(@Body request: RequestBody): ResponseBody

    //Endpoint Login
    @POST("login")
    suspend fun login(@Body request: RequestBody): ResponseBody

    @POST("logout")
    suspend fun logout(): ResponseBody
}