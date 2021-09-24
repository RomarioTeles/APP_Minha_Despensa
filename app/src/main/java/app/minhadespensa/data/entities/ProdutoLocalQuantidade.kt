package app.minhadespensa.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "produto_local_quantidade", primaryKeys = ["produtoId", "localId"])
data class ProdutoLocalQuantidade(
    var produtoId : Int,
    var localId : Int,
    var quantidade : Int
)