package app.minhadespensa.data.repositories

import app.minhadespensa.data.dao.ProdutoLocalQuantidadeDAO
import app.minhadespensa.data.database.AppDB
import app.minhadespensa.data.entities.Local
import app.minhadespensa.data.entities.Produto
import app.minhadespensa.data.entities.ProdutoLocalQuantidade
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ProdutoLocalQuantidadeRepository @Inject constructor(appDB: AppDB){
    
    private val dao = appDB.produtosLocalQuantidadeDAO()

    fun getOne(produtoId : Int, localId: Int) : ProdutoLocalQuantidade{
        return dao.getOne(produtoId, localId)
    }

    suspend fun addQuantidade(produto: Produto, local: Local, quantidade : Int) {
        val produtoLocalQuantidade = getOne(produtoId = produto.id!!, localId = local.id!!)
        produtoLocalQuantidade.let {
            produtoLocalQuantidade.quantidade = produtoLocalQuantidade.quantidade.plus(quantidade)
            dao.update(produtoLocalQuantidade)
        }
    }

    suspend fun subtraiQuantidade(produto: Produto, local: Local, quantidade : Int) {
        val produtoLocalQuantidade = dao.getOne(produtoId = produto.id!!, localId = local.id!!)
        produtoLocalQuantidade.let {
            produtoLocalQuantidade.quantidade = produtoLocalQuantidade.quantidade.minus(quantidade)
            dao.update(produtoLocalQuantidade)
        }
    }

    suspend fun insert(produtoLocalQuantidade: ProdutoLocalQuantidade) {
        dao.insert(produtoLocalQuantidade)
    }

    suspend fun update(produtoLocalQuantidade: ProdutoLocalQuantidade) {
        dao.update(produtoLocalQuantidade)
    }

    suspend fun delete(produtoLocalQuantidade: ProdutoLocalQuantidade) {
        dao.delete(produtoLocalQuantidade)
    }


}