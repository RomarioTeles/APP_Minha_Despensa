package app.minhadespensa.data.dao

import androidx.room.*
import app.minhadespensa.data.entities.ProdutoLocalQuantidade

@Dao
interface ProdutoLocalQuantidadeDAO {

    @Query("SELECT * FROM produto_local_quantidade WHERE produtoId = :produtoId AND localId = :localId")
    fun getOne(produtoId: Int, localId: Int) : ProdutoLocalQuantidade

    @Insert
    suspend fun insert(produtoLocalQuantidade: ProdutoLocalQuantidade)

    @Update
    suspend fun update(produtoLocalQuantidade: ProdutoLocalQuantidade)

    @Delete
    suspend fun delete(produtoLocalQuantidade: ProdutoLocalQuantidade)
}
