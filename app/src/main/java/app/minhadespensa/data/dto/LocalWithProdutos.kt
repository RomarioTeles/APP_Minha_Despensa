package app.minhadespensa.data.dto

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import app.minhadespensa.data.entities.Local
import app.minhadespensa.data.entities.Produto
import app.minhadespensa.data.entities.ProdutoLocalQuantidade

data class LocalWithProdutos(

    @Embedded
    var local: Local,

    @Relation(
        parentColumn = "localId",
        entityColumn = "produtoId",
        associateBy = Junction(ProdutoLocalQuantidade::class)
    )
    var produtos: List<Produto>

)