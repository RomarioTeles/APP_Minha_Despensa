package app.minhadespensa.data.dto

import androidx.room.ColumnInfo
import java.util.*

class LocalDTO {

    @ColumnInfo(name="localId")
    var localId: Int? = null

    @ColumnInfo(name="nome")
    var nome: String? = null

    @ColumnInfo(name="deleteDate")
    var deleteDate: Date? = null

    @ColumnInfo(name="quantidade")
    var quantidadeProdutos: Int? = null
}