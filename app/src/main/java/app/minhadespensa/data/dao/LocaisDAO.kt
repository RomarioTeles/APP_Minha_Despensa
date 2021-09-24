package app.minhadespensa.data.dao

import androidx.room.*
import app.minhadespensa.data.entities.Categoria
import app.minhadespensa.data.entities.Local
import kotlinx.coroutines.flow.Flow

@Dao
interface LocaisDAO {

    @Query("SELECT * FROM locais order by nome")
    fun findAll() : Flow<List<Local>>

    @Query("SELECT * FROM locais WHERE id = :id")
    fun getOne(id: Int): Local

    @Query("SELECT * FROM locais WHERE nome like '%' || :nome || '%' ")
    fun search(nome: String): Local

    @Insert
    suspend fun insert(local: Local)

    @Update
    suspend fun update(local: Local)

    @Delete
    suspend fun delete(local: Local)


}