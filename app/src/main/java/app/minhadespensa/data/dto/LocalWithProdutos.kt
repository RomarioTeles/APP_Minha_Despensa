package app.minhadespensa.data.dto

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import app.minhadespensa.data.entities.EnumStatus
import app.minhadespensa.data.entities.Local
import app.minhadespensa.data.entities.Produto
import app.minhadespensa.data.entities.ProdutoLocalQuantidade

data class LocalWithProdutos(

    @Embedded
    var local: Local,

    @Relation(
        parentColumn = "localId",
        entityColumn = "produtoId",
        entity = Produto::class,
        associateBy = Junction(ProdutoLocalQuantidade::class, parentColumn = "localId", entityColumn = "produtoId")
    )
    var produtos: List<Produto>,

    @ColumnInfo(name="quantidade")
    var quantidade: Int,

    @ColumnInfo(name = "status")
    var status: EnumStatus

)