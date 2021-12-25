package app.minhadespensa.data.repositories

import android.util.Log
import app.minhadespensa.data.database.AppDB
import app.minhadespensa.data.dto.LocalWithProdutos
import app.minhadespensa.data.dto.ProdutoDTO
import app.minhadespensa.data.dto.ProdutosWithLocais
import app.minhadespensa.data.entities.Produto
import app.minhadespensa.data.entities.ProdutoLocalQuantidade
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ProdutosRepository @Inject constructor(appDB: AppDB, val plqRepository: ProdutoLocalQuantidadeRepository) {

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

    fun findLocaisByProdutoId(produtoId: Int): Flow<List<LocalWithProdutos>>{
        return plqRepository.findByProdutoId(produtoId)
    }

    suspend fun insert(produto: Produto) {

        val locais = produto.locais.toTypedArray()

        if(produto.produtoId.equals(0)){
            val produtoId = dao.insert(produto)
            locais.forEach { it.produtoId = produtoId.toInt() }
        }else{
            plqRepository.deleteAllByProdutoId(produto.produtoId)
            dao.update(produto)
        }

        plqRepository.insert(*locais)

    }

    suspend fun update(produto: Produto) {
        dao.update(produto)
    }

    suspend fun delete(produto: Produto) {
        dao.delete(produto)
    }

}