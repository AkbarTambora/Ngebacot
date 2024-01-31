// HomeViewModel.kt
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.ngebacot.core.data.local.auth.AuthLocalDatastore
import com.example.ngebacot.core.data.remote.client.ApiService
import com.example.ngebacot.core.utils.checkInternetConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HomeViewModel(
    private val apiService: ApiService,
    private val context: Context
) {
    val postSuccessState: MutableState<Boolean> = mutableStateOf(false)

    // State untuk menyimpan data post
    val postsState: MutableState<List<PostModel>> = mutableStateOf(emptyList())

    // State untuk menampilkan loading saat data sedang dimuat
    val loadingState: MutableState<Boolean> = mutableStateOf(false)

    // State untuk menampilkan indikator refresh
    val isRefreshing: MutableState<Boolean> = mutableStateOf(false)

    // State untuk menyimpan pesan kesalahan
    val errorState: MutableState<String?> = mutableStateOf(null)

    // Fungsi untuk memperbarui data post dari server
    fun fetchPosts(coroutineScope: CoroutineScope) {
        // Reset error state
        errorState.value = null

        // Lakukan pengecekan koneksi internet sebelum melakukan fetching data
        if (!checkInternetConnection(context)) {
            errorState.value = "No internet connection"
            loadingState.value = false
            isRefreshing.value = false
            return
        }

        val token = AuthLocalDatastore.getToken(context)

        coroutineScope.launch {
            try {
                val headers = if (!token.isNullOrEmpty()) {
                    mapOf("Authorization" to "Bearer $token")
                } else {
                    emptyMap()
                }
                // Panggil method getPost dari ApiService untuk mengambil data post
                val response = apiService.getPosts(headers = headers)
                if (response.isSuccessful) {
                    val postResponse = response.body()
                    if (postResponse != null) {
                        postsState.value = postResponse.data
                    }
                } else {
                    // Handle kesalahan jika diperlukan
                    errorState.value = "Failed to fetch posts: ${response.code()}"
                }
            } catch (e: Exception) {
                // Handle exception jika diperlukan
                errorState.value = "An error occurred: ${e.message}"
            } finally {
                // Set loadingState menjadi false setelah selesai memuat data
                loadingState.value = false
                isRefreshing.value = false
            }
        }
    }
}