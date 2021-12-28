package app.minhadespensa.data.dao

import androidx.room.*
import app.minhadespensa.data.entities.Categoria
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriasDAO {

    @Query("SELECT * FROM categorias WHERE deleteDate is null order by nome")
    fun findAll() : Flow<List<Categoria>>

    @Query("SELECT * FROM categorias WHERE categoriaId = :id")
    fun getOne(id: Int): Categoria

    @Query("SELECT * FROM categorias WHERE nome like '%' || :nome || '%' ")
    fun search(nome: String): Categoria

    @Insert
    suspend fun insert(categoria: Categoria)

    @Update
    suspend fun update(categoria: Categoria)

    @Delete
    suspend fun delete(categoria: Categoria)


}