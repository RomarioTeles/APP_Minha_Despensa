package app.minhadespensa.data.repositories

import app.minhadespensa.data.database.AppDB
import app.minhadespensa.data.dto.LocalWithProdutos
import app.minhadespensa.data.dto.ProdutoQuantidade
import app.minhadespensa.data.dto.ProdutosWithLocais
import app.minhadespensa.data.entities.Local
import app.minhadespensa.data.entities.Produto
import app.minhadespensa.data.entities.ProdutoLocalQuantidade
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ProdutoLocalQuantidadeRepository @Inject constructor(appDB: AppDB){
    
    private val dao = appDB.produtosLocalQuantidadeDAO()

    fun findAll(): Flow<List<LocalWithProdutos>>{
        return dao.findAll()
    }

    fun findByLocalId(localId: Int): Flow<List<ProdutosWithLocais>> {
        return dao.findByLocalId(localId)
    }

    fun pesquisarProduto(search: String): Flow<List<ProdutoQuantidade>>{
        return dao.pesquisarProduto(search)
    }

    fun getOne(produtoId : Int, localId: Int) : ProdutoLocalQuantidade{
        return dao.getOne(produtoId, localId)
    }

    suspend fun addQuantidade(produto: Produto, local: Local, quantidade : Int) {
        val produtoLocalQuantidade = getOne(produtoId = produto.produtoId!!, localId = local.localId!!)
        produtoLocalQuantidade.let {
            produtoLocalQuantidade.quantidade = produtoLocalQuantidade.quantidade.plus(quantidade)
            dao.update(produtoLocalQuantidade)
        }
    }

    suspend fun subtraiQuantidade(produto: Produto, local: Local, quantidade : Int) {
        val produtoLocalQuantidade = dao.getOne(produtoId = produto.produtoId!!, localId = local.localId!!)
        produtoLocalQuantidade.let {
            produtoLocalQuantidade.quantidade = produtoLocalQuantidade.quantidade.minus(quantidade)
            dao.update(produtoLocalQuantidade)
        }
    }

    suspend fun insert(vararg produtoLocalQuantidade: ProdutoLocalQuantidade) {
        dao.insert(*produtoLocalQuantidade)
    }

    suspend fun update(produtoLocalQuantidade: ProdutoLocalQuantidade) {
        dao.update(produtoLocalQuantidade)
    }

    suspend fun delete(produtoLocalQuantidade: ProdutoLocalQuantidade) {
        dao.delete(produtoLocalQuantidade)
    }

    fun findByProdutoId(produtoId: Int): Flow<List<LocalWithProdutos>> {
        return dao.findByProdutoId(produtoId)
    }

    fun deleteAllByProdutoId(produtoId: Int){
        dao.deleteAllByProdutoId(produtoId)
    }

}