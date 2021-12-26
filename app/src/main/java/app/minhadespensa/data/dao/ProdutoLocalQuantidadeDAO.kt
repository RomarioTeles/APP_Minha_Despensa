package app.minhadespensa.data.dao

import androidx.room.*
import app.minhadespensa.data.dto.LocalWithProdutos
import app.minhadespensa.data.dto.ProdutoQuantidade
import app.minhadespensa.data.dto.ProdutosWithLocais
import app.minhadespensa.data.entities.ProdutoLocalQuantidade
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoLocalQuantidadeDAO {

    @Query("SELECT * FROM produto_local_quantidade WHERE produtoId = :produtoId AND localId = :localId")
    fun getOne(produtoId: Int, localId: Int) : ProdutoLocalQuantidade

    @Insert
    suspend fun insert(vararg produtoLocalQuantidade: ProdutoLocalQuantidade)


    @Update
    suspend fun update(produtoLocalQuantidade: ProdutoLocalQuantidade)

    @Delete
    suspend fun delete(produtoLocalQuantidade: ProdutoLocalQuantidade)

    @Transaction
    @Query("""SELECT * FROM produto_local_quantidade pl JOIN locais l ON l.localId = pl.localId""")
    fun findAll(): Flow<List<LocalWithProdutos>>

    @Transaction
    @Query("""SELECT * FROM produto_local_quantidade pl JOIN locais l ON l.localId = pl.localId WHERE pl.produtoId = :produtoId""")
    fun findByProdutoId(produtoId: Int): Flow<List<LocalWithProdutos>>

    @Transaction
    @Query("""SELECT * FROM produto_local_quantidade pl JOIN produtos p ON p.produtoId = pl.produtoId Where localId = :localId""")
    fun findByLocalId(localId: Int): Flow<List<ProdutosWithLocais>>

    @Transaction
    @Query("""SELECT p.produtoId, p.categoriaId, p.nome, p.codigo, 
         (select SUM(sq1.quantidade) from produto_local_quantidade sq1 WHERE sq1.produtoId = p.produtoId ) as quantidade 
        FROM produto_local_quantidade pl JOIN produtos p ON p.produtoId = pl.produtoId 
        WHERE p.codigo like '%' || :search || '%' or p.nome  like '%' || :search || '%' 
        Group By p.produtoId, p.categoriaId, p.nome, p.codigo, quantidade """)
    fun pesquisarProduto(search: String): Flow<List<ProdutoQuantidade>>

    @Query("DELETE FROM produto_local_quantidade WHERE produtoId = :produtoId")
    fun deleteAllByProdutoId(produtoId: Int)
}
