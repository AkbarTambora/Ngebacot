
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ngebacot.core.utils.DateUtils
import com.example.ngebacot.screens.BacotanUser
import com.example.ngebacot.screens.IconTwitters
import com.example.ngebacot.screens.MainContent
import com.example.ngebacot.screens.Message
import kotlinx.coroutines.delay

@Composable
fun HomePage(
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() } // Deklarasi SnackbarHostState

    LaunchedEffect(homeViewModel.errorState.value) {
        // Menampilkan Snackbar jika terjadi kesalahan
        val errorMessage = homeViewModel.errorState.value
        if (!errorMessage.isNullOrEmpty()) {
            snackbarHostState.showSnackbar(message = errorMessage)
            delay(3000) // Menutup Snackbar setelah 3 detik
            snackbarHostState.currentSnackbarData?.dismiss()
        }
    }

    LaunchedEffect(Unit) {
        // Memperbarui data post dari server saat komponen pertama kali dijalankan
        homeViewModel.fetchPosts(coroutineScope)
    }

    IconTwitters() // Panggil IconTwitters tanpa parameter onPostSuccess karena tidak digunakan dalam fungsi ini

    // Tampilkan loading indicator jika data sedang dimuat
    if (homeViewModel.loadingState.value) {
        CircularProgressIndicator() // Tampilkan indikator progres
    } else {
        // Tampilkan data post jika tidak ada kesalahan dan data telah dimuat
        LazyColumn {
            items(homeViewModel.postsState.value) { post ->
                val createdAt = DateUtils().localDateTime(post.created_at) // Convert string to LocalDateTime
                BacotanUser(
                    msg = Message(
                        username = post.user.username,
                        nama = post.user.name ?: "", // Handle nilai null untuk user name
                        created_at = createdAt,
                        body = post.caption
                    )
                )
            }
        }
    }

    // Tambahkan komponen SnackbarHost untuk menampilkan Snackbar
    SnackbarHost(hostState = snackbarHostState)
    // Tambahkan MainContent di sini untuk menampilkan sebagai snackbar
    if (homeViewModel.postSuccessState.value) {
        MainContent()
    }

}
