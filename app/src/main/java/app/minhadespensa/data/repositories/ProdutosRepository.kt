package app.minhadespensa.data.repositories

import app.minhadespensa.data.database.AppDB
import app.minhadespensa.data.dto.ProdutoDTO
import app.minhadespensa.data.entities.Produto
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ProdutosRepository @Inject constructor(appDB: AppDB) {

    private val dao = appDB.produtosDAO()

    private val produtoLocalQuantDAO = appDB.produtosLocalQuantidadeDAO()

    fun findAll(): Flow<List<ProdutoDTO>> {
        return dao.findAll()
    }

    fun getOne(id: Int): ProdutoDTO {
        return dao.getOne(id)
    }

    fun search(nome: String): Flow<List<ProdutoDTO>> {
        return dao.search(nome)
    }

    fun searchByCategoria(id: Int): Flow<List<ProdutoDTO>> {
        return dao.searchByCategoria(id)
    }

    fun searchByLocal(id: Int): Flow<List<ProdutoDTO>> {
        return dao.searchByLocal(id)
    }

    suspend fun insert(produto: Produto) {
        dao.insert(produto)
    }

    suspend fun update(produto: Produto) {
        dao.update(produto)
    }

    suspend fun delete(produto: Produto) {
        dao.delete(produto)
    }

}