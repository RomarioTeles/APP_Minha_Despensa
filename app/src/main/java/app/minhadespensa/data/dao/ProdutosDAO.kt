package app.minhadespensa.data.dao

import androidx.room.*
import app.minhadespensa.data.dto.ProdutoDTO
import app.minhadespensa.data.entities.Produto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutosDAO {

    @Query("SELECT * FROM produtos order by nome")
    fun findAll() : Flow<List<ProdutoDTO>>

    @Query("SELECT * FROM produtos WHERE id = :id")
    fun getOne(id: Int): ProdutoDTO

    @Query("SELECT * FROM produtos WHERE nome like '%' || :nome || '%' ")
    fun search(nome: String): Flow<List<ProdutoDTO>>

    @Query("SELECT * FROM produtos p JOIN categorias c ON c.id = p.categoriaId WHERE c.id = :id order by nome")
    fun searchByCategoria(id: Int) : Flow<List<ProdutoDTO>>

    @Query("SELECT * FROM produtos p JOIN produto_local_quantidade l ON l.localId = p.id WHERE l.localId = :id order by nome")
    fun searchByLocal(id: Int) : Flow<List<ProdutoDTO>>

    @Insert
    suspend fun insert(produto: Produto)

    @Update
    suspend fun update(produto: Produto)

    @Delete
    suspend fun delete(produto: Produto)
}