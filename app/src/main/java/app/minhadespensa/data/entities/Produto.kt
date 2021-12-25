package app.minhadespensa.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "produtos")
data class Produto(
    @PrimaryKey(autoGenerate = true) var produtoId: Int,
    var nome: String,
    var codigo: String?=null,
    var categoriaId: Int
) : Parcelable{

    @IgnoredOnParcel
    @Ignore var locais: MutableList<ProdutoLocalQuantidade> = mutableListOf()

    @IgnoredOnParcel
    @Ignore @ColumnInfo(name = "nomeCategoria") var nomeCategoria: String? = null
}