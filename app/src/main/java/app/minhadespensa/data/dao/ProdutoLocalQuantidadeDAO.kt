package app.minhadespensa.data.dao

import androidx.room.*
import app.minhadespensa.data.dto.LocalWithProdutos
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
    @Query("SELECT *, c.nome as nomeCategoria FROM produto_local_quantidade pl JOIN produtos p ON p.produtoId = pl.produtoId JOIN categorias c ON c.categoriaId = p.categoriaId")
    fun findAll(): Flow<List<LocalWithProdutos>>

    @Transaction
    @Query("SELECT * FROM produto_local_quantidade pl JOIN produtos p ON p.produtoId = pl.produtoId WHERE p.codigo like '%' || :search || '%' or p.nome  like '%' || :search || '%'")
    fun pesquisarProduto(search: String): Flow<List<ProdutosWithLocais>>
}
