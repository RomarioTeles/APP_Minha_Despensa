package app.minhadespensa.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "produtos")
data class Produto(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var nome: String,
    var codigo: String?=null,
    var categoriaId: Int,
    var status: EnumStatus = EnumStatus.FECHADO
) : Parcelable