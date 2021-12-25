package app.minhadespensa.data.dto

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import app.minhadespensa.data.entities.EnumStatus
import app.minhadespensa.data.entities.Local
import app.minhadespensa.data.entities.Produto
import app.minhadespensa.data.entities.ProdutoLocalQuantidade

data class ProdutosWithLocais(

    @Embedded
    val produto: Produto,

    @Relation(
        parentColumn = "produtoId",
        entityColumn = "localId",
        associateBy = Junction(
            ProdutoLocalQuantidade::class,
            parentColumn = "produtoId",
            entityColumn = "localId"
        )
    )
    val locais: List<Local>,

    @ColumnInfo(name="quantidade")
    var quantidade: Int,

    @ColumnInfo(name = "status")
    var status: EnumStatus
)