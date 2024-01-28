package com.example.ngebacot.core.data.remote.client

import android.content.Context
import com.example.ngebacot.core.data.local.auth.AuthLocalDatastore
import com.example.ngebacot.core.utils.AppConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient(context:Context){
    /*
    *  Memasukkan JWT Token kedalam header
    */
    private val authInterceptor = Interceptor { chain ->
        val token = AuthLocalDatastore.getToken(context)
        if (!token.isNullOrEmpty()) {
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(request)
        } else {
            // Token null atau kosong, lakukan penanganan di sini
            println("Error: Token is null or empty")
            // Misalnya, tampilkan pesan kesalahan atau lakukan tindakan yang sesuai
            chain.proceed(chain.request()) // Lanjutkan permintaan tanpa token
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    /*
     * Add a function to create the ApiService instance
    */
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}