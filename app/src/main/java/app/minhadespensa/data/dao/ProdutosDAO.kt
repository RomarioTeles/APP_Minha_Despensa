package app.minhadespensa.data.dao

import androidx.room.*
import app.minhadespensa.data.dto.ProdutoDTO
import app.minhadespensa.data.entities.Produto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutosDAO {

    @Transaction
    @Query("SELECT * FROM produtos order by nome")
    fun findAll() : Flow<List<ProdutoDTO>>

    @Transaction
    @Query("SELECT * FROM produtos WHERE produtoId = :id")
    fun getOne(id: Int): ProdutoDTO

    @Transaction
    @Query("SELECT * FROM produtos WHERE nome like '%' || :nome || '%' ")
    fun search(nome: String): Flow<List<ProdutoDTO>>

    @Transaction
    @Query("SELECT * FROM produtos p JOIN categorias c ON c.categoriaId = p.categoriaId WHERE c.categoriaId = :id order by nome")
    fun searchByCategoria(id: Int) : Flow<List<ProdutoDTO>>

    @Insert
    suspend fun insert(produto: Produto) : Long

    @Update
    suspend fun update(produto: Produto)

    @Delete
    suspend fun delete(produto: Produto)
}