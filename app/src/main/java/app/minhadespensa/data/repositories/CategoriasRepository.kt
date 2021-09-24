package app.minhadespensa.data.repositories

import app.minhadespensa.data.database.AppDB
import app.minhadespensa.data.entities.Categoria
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class CategoriasRepository @Inject constructor(appDB: AppDB){

    private val dao = appDB.categoriasDAO()

    fun findAll(): Flow<List<Categoria>> {
        return dao.findAll()
    }

    fun getOne(id: Int): Categoria {
        return dao.getOne(id)
    }

    fun search(nome: String): Categoria {
        return dao.search(nome)
    }

    suspend fun insert(categoria: Categoria) {
       dao.insert(categoria)
    }

    suspend fun update(categoria: Categoria) {
        dao.update(categoria)
    }

    suspend fun delete(categoria: Categoria) {
        dao.delete(categoria)
    }

}