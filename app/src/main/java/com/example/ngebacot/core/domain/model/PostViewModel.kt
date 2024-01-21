package com.example.ngebacot.core.domain.model

import androidx.lifecycle.ViewModel
import com.example.ngebacot.core.data.remote.client.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostViewModel(private val apiService: ApiService) : ViewModel() {

//    baris 11 harusnya Post? bukan PostModel? karena dia ambil response API tapi dia ga mau ambil malah error. Nanti benerin
    suspend fun createPost(token: String, postText: String): PostModel? {
        return withContext(Dispatchers.IO) {
            try {
                val postRequest = PostModel(text = postText)
                val response = apiService.createPost("Bearer $token", postRequest)
                response.body()
            } catch (e: Exception) {
                // Handle error
                null
            }
        }
    }
}
