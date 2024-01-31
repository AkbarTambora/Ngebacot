import com.example.ngebacot.core.domain.model.UserModel
import com.example.ngebacot.core.utils.DateUtils // Pastikan Anda mengganti ini dengan lokasi sebenarnya dari DateUtils
import kotlinx.serialization.Serializable
import java.time.LocalDateTime // Pastikan Anda menggunakan java.time.LocalDateTime

@Serializable
data class PostModel (
    val id: Int,
    val caption: String,
    val img: String?,
    val userId: Int,
    val created_at: String,
    val user: UserModel
) {
    // Mengonversi string tanggal dan waktu ke LocalDateTime menggunakan DateUtil
    val createdAts: LocalDateTime
        get() = DateUtils().localDateTime(created_at)
}
