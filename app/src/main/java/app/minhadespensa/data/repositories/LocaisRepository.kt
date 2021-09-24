package app.minhadespensa.data.repositories

import app.minhadespensa.data.dao.LocaisDAO
import app.minhadespensa.data.database.AppDB
import app.minhadespensa.data.entities.Categoria
import app.minhadespensa.data.entities.Local
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class LocaisRepository  @Inject constructor(appDB: AppDB){

    private val dao = appDB.locaisDAO()

    fun findAll(): Flow<List<Local>> {
        return dao.findAll()
    }

    fun getOne(id: Int): Local {
        return dao.getOne(id)
    }

    fun search(nome: String): Local {
        return dao.search(nome)
    }

    suspend fun insert(local: Local) {
        dao.insert(local)
    }

    suspend fun update(local: Local) {
       dao.update(local)
    }

    suspend fun delete(local: Local) {
        dao.delete(local)
    }


}