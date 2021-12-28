package app.minhadespensa.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.*

@Parcelize
@Entity(tableName = "categorias")
data class Categoria(
    @PrimaryKey(autoGenerate = true) var categoriaId : Int? = null,
    var nome : String,
    var deleteDate: Date? = null
) : Parcelable