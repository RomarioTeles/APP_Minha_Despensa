package app.minhadespensa.data.dto

import androidx.room.Embedded
import androidx.room.Relation
import app.minhadespensa.data.entities.Categoria
import app.minhadespensa.data.entities.Produto


data class ProdutoDTO(
    @Embedded var produto: Produto,
    @Relation(
        parentColumn = "produtoId",
        entityColumn = "categoriaId"
    )
    var categoria: Categoria

)