package app.minhadespensa.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "produto_local_quantidade", primaryKeys = ["produtoId", "localId"])
data class ProdutoLocalQuantidade(
    @ColumnInfo(name="produtoId", index = true) var produtoId : Int,
    @ColumnInfo(name="localId", index = true) var localId : Int,
    @ColumnInfo(name="quantidade") var quantidade : Int,
    @ColumnInfo(name="status") var status: EnumStatus = EnumStatus.FECHADO
){
    @Ignore var localNome : String? = null

}